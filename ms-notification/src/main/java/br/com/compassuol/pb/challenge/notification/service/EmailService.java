package br.com.compassuol.pb.challenge.notification.service;

import br.com.compassuol.pb.challenge.notification.entity.Email;
import br.com.compassuol.pb.challenge.notification.repository.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private EmailRepository emailRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private JavaMailSender emailSender;

    public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    public void sendEmail(Email email) throws MessagingException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();

        var mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);


        mimeMessageHelper.setFrom(email.getFromEmail());
        mimeMessageHelper.setCc(email.getReplyTo());
        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setText(email.getBody());
        mimeMessage.setContent(email.getBody(),email.getContentType());

        emailRepository.save(email);
        emailSender.send(mimeMessage);
    }


}
