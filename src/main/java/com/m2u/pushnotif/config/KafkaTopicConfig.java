package com.m2u.pushnotif.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.template.default-topic}")
    private String kafkaTopic;

    @Bean
    public NewTopic topicPushNotif(){
        return TopicBuilder.name(kafkaTopic)
                .partitions(5)
                .replicas(1)
                .build();
    }
}
