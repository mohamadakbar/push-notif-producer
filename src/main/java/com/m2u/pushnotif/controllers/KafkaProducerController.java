package com.m2u.pushnotif.controllers;

import com.m2u.pushnotif.models.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class KafkaProducerController {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerController.class);

    @Autowired
    private KafkaTemplate<String, MessageModel> kafkaTemplate;

    @Value(value = "${spring.kafka.template.default-topic}")
    private String kafkaTopicName;

    String status = "";

    @PostMapping(path = "/messages", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> sendMessage(@RequestBody String request, MessageModel messageModel) {
        messageModel.setContent(request);
        logger.info("sending message {}", messageModel);
        ListenableFuture<SendResult<String, MessageModel>> future = this.kafkaTemplate.send(kafkaTopicName,
                messageModel);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, MessageModel> result) {
                status = "Message sent successfully";
                logger.info("successfully sent message = {}, with offset = {}", messageModel,
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("Failed to send message = {}, error = {}", messageModel, ex.getMessage());
                status = "Message sending failed";
            }
        });
        return ResponseEntity.ok(status);
    }

}
