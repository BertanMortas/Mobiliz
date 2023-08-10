package com.mobiliz.rabbitmq.producer;

import com.mobiliz.rabbitmq.model.ForgotPasswordMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgotPasswordMailProducer {

    @Value("${rabbitmq.exchange}")
    String exchange;
    @Value("${rabbitmq.forgotPasswordBindingKey}")
    String forgotPasswordBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendForgotPasswordMail(ForgotPasswordMailModel forgotPasswordMailModel){
        rabbitTemplate.convertAndSend(exchange, forgotPasswordBindingKey, forgotPasswordMailModel);
    }

}
