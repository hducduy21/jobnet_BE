package com.jobnet.common.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic userRegistrationTopic() {
        return TopicBuilder.name("user-registration").build();
    }

    @Bean
    public NewTopic elasticPostCreatedTopic() {
    return TopicBuilder.name("post-elastic-creation").build();
}
}
