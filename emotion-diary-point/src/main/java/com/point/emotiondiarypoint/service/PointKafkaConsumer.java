package com.point.emotiondiarypoint.service;

import com.point.emotiondiarypoint.api.request.PointHistoryRequest;
import com.point.emotiondiarypoint.dto.PointDto;
import com.point.emotiondiarypoint.dto.PointHistoryDto;
import com.point.emotiondiarypoint.mapper.PointHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointKafkaConsumer {

  private final PointService service;
  private final PointHistoryMapper historyMapper;

  @KafkaListener(topics = "point-add", groupId = "point-group")
  public void add(PointHistoryRequest request) {
    PointHistoryDto dto = historyMapper.toDto(request);
    PointDto pointDto = service.getOrSave(request.getMemberId());
    dto.setPointId(pointDto.getId());
    PointHistoryDto savedDto = service.add(dto);
    log.info("PointKafkaConsumer.add > memberId : {}, pointId : {}, pointHistoryId : {}", pointDto.getMemberId(), pointDto.getId(), savedDto.getId());
  }
}
