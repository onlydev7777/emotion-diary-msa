package com.point.emotiondiarypoint.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {

  private Long id;
  private Long memberId;
  private int score;
  private List<PointHistoryDto> historyDtoList;
}
