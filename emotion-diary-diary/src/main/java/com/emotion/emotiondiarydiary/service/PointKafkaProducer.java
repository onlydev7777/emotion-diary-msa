package com.emotion.emotiondiarydiary.service;

import com.emotion.emotiondiarydiary.dto.PointHistoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointKafkaProducer {

  private static final String TOPIC = "point-add";
  private final KafkaTemplate<String, PointHistoryRequest> kafkaTemplate;

  public void send(PointHistoryRequest request) {
    kafkaTemplate.send(TOPIC, request);
    log.info("{} 토픽 전송! memberId : {}", TOPIC, request.getMemberId());
  }
}
