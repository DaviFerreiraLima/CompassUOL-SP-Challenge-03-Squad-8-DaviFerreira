package br.com.compassuol.pb.challenge.msproducts.publisher;

import br.com.compassuol.pb.challenge.msproducts.payload.EmailDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RabbitMQProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMQProducer rabbitMQProducer;

    @Test
    void sendMessage() {
        EmailDto emailDto = new EmailDto();
        emailDto.setFromEmail("from@example.com");
        emailDto.setFromName("John Doe");
        emailDto.setReplyTo("replyto@example.com");
        emailDto.setTo("to@example.com");
        emailDto.setSubject("Test Subject");
        emailDto.setBody("Test Body");
        emailDto.setContentType("text/plain");

        doNothing().when(rabbitTemplate).convertAndSend("mail_exchange", "email_routing_key", emailDto);
        rabbitMQProducer.sendMessage(emailDto);

        verify(rabbitTemplate).convertAndSend("mail_exchange", "email_routing_key", emailDto);
    }
}