package me.lam.huyen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.test.rule.KafkaEmbedded;

@Configuration
public class KafkaEmbeddedConfig {

    @Bean
    public KafkaEmbedded kafkaEmbedded() {
        KafkaEmbedded kafkaEmbedded = new KafkaEmbedded(1, false, 1);
        kafkaEmbedded.setKafkaPorts(9092);
        return kafkaEmbedded;
    }
}
