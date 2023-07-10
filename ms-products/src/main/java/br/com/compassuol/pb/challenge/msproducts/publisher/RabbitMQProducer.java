package br.com.compassuol.pb.challenge.msproducts.publisher;

import br.com.compassuol.pb.challenge.msproducts.payload.EmailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange ;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey ;

    private final Logger LOGGER  = LoggerFactory.getLogger(RabbitMQProducer.class);
    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public  void sendMessage(EmailDto emailDto) {
        LOGGER.info(String.format("sent to ->%s",emailDto.toString()));
        rabbitTemplate.convertAndSend(exchange,routingKey,emailDto);
    }


}
