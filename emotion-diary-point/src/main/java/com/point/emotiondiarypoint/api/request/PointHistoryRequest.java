package com.point.emotiondiarypoint.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointHistoryRequest {

  private Long pointId;
  private Long memberId;
  private int score;
  private String details;
}
