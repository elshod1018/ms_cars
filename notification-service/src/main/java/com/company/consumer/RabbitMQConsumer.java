package com.company.consumer;

import com.company.dto.MessageSendDTO;
import com.company.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private final MailService mailService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeMessage(MessageSendDTO dto) {
        log.info("Json message received: {}", dto.toString());
        mailService.sendEmail(dto.email(), dto.code());
    }
}
