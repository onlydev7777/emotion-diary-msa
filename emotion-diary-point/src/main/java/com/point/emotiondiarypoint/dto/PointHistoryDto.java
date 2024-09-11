package com.point.emotiondiarypoint.dto;

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
public class PointHistoryDto {

  private Long id;
  private Long pointId;
  private int score;
  private String details;

  public void setPointId(Long pointId) {
    this.pointId = pointId;
  }
}
