package com.mobiliz.rabbitmq.consumer;

import com.mobiliz.rabbitmq.model.ForgotPasswordMailModel;
import com.mobiliz.rabbitmq.model.RegisterMailModel;
import com.mobiliz.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailConsumer {
    private final MailService mailService;

    @RabbitListener(queues = ("${rabbitmq.mailQueue}"))
    public void sendActivationLink(RegisterMailModel model) {mailService.createMail(model);}

    @RabbitListener(queues = ("${rabbitmq.forgotPasswordQueue}"))
    public void sendForgotPasswordMail(ForgotPasswordMailModel forgotPasswordMailModel){
        mailService.sendMailForgetPassword(forgotPasswordMailModel);
    }
}
