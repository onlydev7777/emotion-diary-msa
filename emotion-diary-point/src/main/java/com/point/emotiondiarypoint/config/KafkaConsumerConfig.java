package com.point.emotiondiarypoint.config;

import com.point.emotiondiarypoint.api.request.PointHistoryRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  @Bean
  public ConsumerFactory<String, PointHistoryRequest> consumerFactory() {
    Map<String, Object> properties = new HashMap<>();

    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:7070");
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "point-group");
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

    return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(PointHistoryRequest.class, false));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, PointHistoryRequest> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, PointHistoryRequest> kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
    kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

    return kafkaListenerContainerFactory;
  }
}
