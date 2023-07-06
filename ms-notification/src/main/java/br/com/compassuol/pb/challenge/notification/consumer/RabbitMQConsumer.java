package br.com.compassuol.pb.challenge.notification.consumer;

import br.com.compassuol.pb.challenge.notification.entity.Email;
import br.com.compassuol.pb.challenge.notification.payload.EmailDto;
import br.com.compassuol.pb.challenge.notification.service.EmailService;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    private EmailService emailService;
    private ModelMapper mapper;

    public RabbitMQConsumer(EmailService emailService,ModelMapper mapper) {
        this.emailService = emailService;
        this.mapper = mapper;
    }

    private final Logger LOGGER  = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = {"${spring.rabbitmq.template.default-receive-queue}"})
    public void consumeEmail(@Payload EmailDto emailDto) throws MessagingException {
        emailDto.setReplyTo(emailDto.getTo());
        LOGGER.info(String.format("Recieved emailDto -> %s",emailDto.toString()));
        var email = mapper.map(emailDto, Email.class);
        emailService.sendEmail(email);
    }

}
