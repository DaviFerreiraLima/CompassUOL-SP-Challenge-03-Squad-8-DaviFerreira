package br.com.compassuol.pb.challenge.notification.controller;

import br.com.compassuol.pb.challenge.notification.entity.Email;
import br.com.compassuol.pb.challenge.notification.payload.EmailDto;
import br.com.compassuol.pb.challenge.notification.repository.EmailRepository;
import br.com.compassuol.pb.challenge.notification.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailController {

    private EmailService emailService;
    private ModelMapper mapper;

    public EmailController(EmailService emailService,ModelMapper mapper)
    {
        this.emailService = emailService;
        this.mapper = mapper;
    }

    @PostMapping()
        public ResponseEntity<Email> sendingEmail(@RequestBody @Valid EmailDto emailDto) throws MessagingException {
            var email = mapper.map(emailDto,Email.class);
            emailService.sendEmail(email);
            return new ResponseEntity<>(email, HttpStatus.CREATED);
        }
}
