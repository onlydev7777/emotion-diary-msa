package com.point.emotiondiarypoint.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.point.emotiondiarypoint.IntegrationTestSupport;
import com.point.emotiondiarypoint.TestComponent;
import com.point.emotiondiarypoint.api.request.PointHistoryRequest;
import com.point.emotiondiarypoint.api.request.PointRequest;
import com.point.emotiondiarypoint.dto.PointDto;
import com.point.emotiondiarypoint.dto.PointHistoryDto;
import com.point.emotiondiarypoint.mapper.PointHistoryMapper;
import com.point.emotiondiarypoint.mapper.PointMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PointServiceTest extends IntegrationTestSupport {

  @Autowired
  private PointService service;

  @Autowired
  private TestComponent testComponent;

  @Autowired
  private PointMapper pointMapper;

  @Autowired
  private PointHistoryMapper historyMapper;

  @BeforeEach
  void setUp() {
    testComponent.defaultPointSetUp();
  }

  @Test
  void 포인트_최초_생성() {
    //given
    PointRequest pointRequest = new PointRequest(1L);
    PointDto requestDto = pointMapper.toDto(pointRequest);
    PointDto savedPointDto = service.save(requestDto);

    //when
    PointDto findPointDto = service.get(savedPointDto.getMemberId());

    //then
    assertThat(requestDto.getMemberId()).isEqualTo(savedPointDto.getMemberId());
    assertThat(savedPointDto.getScore()).isEqualTo(0);
    assertThat(savedPointDto.getHistoryDtoList()).hasSize(0);
    assertThat(savedPointDto).isEqualTo(findPointDto);
  }

  @Test
  void 포인트_추가() {
    //given 1: point
    PointRequest pointRequest = new PointRequest(1L);
    PointDto requestDto = pointMapper.toDto(pointRequest);

    PointDto savedPointDto = service.save(requestDto);

    //given 2: point history
    PointHistoryRequest request = new PointHistoryRequest(savedPointDto.getId(), 1L, 10, "일기쓰기");
    PointHistoryDto requestHistoryDto = historyMapper.toDto(request);
    PointHistoryDto savedHistoryDto = service.add(requestHistoryDto);

    //when
    PointDto findPointDto = service.get(savedPointDto.getMemberId());

    //then
    assertThat(savedPointDto.getId()).isEqualTo(findPointDto.getId());
    assertThat(findPointDto.getScore()).isEqualTo(10);
    assertThat(findPointDto.getHistoryDtoList()).hasSize(1);
    assertThat(findPointDto.getHistoryDtoList().get(0)).isEqualTo(savedHistoryDto);
  }
}