package com.emotion.emotiondiarydiary.config;

import com.emotion.emotiondiarydiary.dto.PointHistoryRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

  @Bean
  public ProducerFactory<String, PointHistoryRequest> producerFactory() {
    Map<String, Object> properties = new HashMap<>();

    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:7070");
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

    return new DefaultKafkaProducerFactory<>(properties);
  }

  @Bean
  public KafkaTemplate<String, PointHistoryRequest> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}
