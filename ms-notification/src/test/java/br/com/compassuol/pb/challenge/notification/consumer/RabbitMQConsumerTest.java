package br.com.compassuol.pb.challenge.notification.consumer;

import br.com.compassuol.pb.challenge.notification.entity.Email;
import br.com.compassuol.pb.challenge.notification.payload.EmailDto;
import br.com.compassuol.pb.challenge.notification.service.EmailService;
import br.com.compassuol.pb.challenge.notification.util.EmailUtil;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class RabbitMQConsumerTest {

    @Mock
    private EmailService emailService;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private RabbitMQConsumer rabbitMQConsumer;

    @Test
    void consumeEmail() throws MessagingException {
        var emailDto = EmailUtil.createEmailDto();

        doNothing().when(emailService).sendEmail(any(Email.class));

        rabbitMQConsumer.consumeEmail(emailDto);

        verify(emailService).sendEmail(any(Email.class));
    }
}