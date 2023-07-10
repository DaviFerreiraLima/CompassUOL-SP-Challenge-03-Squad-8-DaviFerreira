package br.com.compassuol.pb.challenge.msproducts.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.template.default-receive-queue}")
    private String queueName;

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange = "email_exchange";

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey = "email_routing_key";
    @Bean
    public Queue queue(){
        return  new Queue(queueName,true);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(topicExchange())
                .with(routingKey);
    }
}
