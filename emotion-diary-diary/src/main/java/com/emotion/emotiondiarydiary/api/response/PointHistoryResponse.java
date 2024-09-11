package com.emotion.emotiondiarydiary.api.response;

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
public class PointHistoryResponse {

  private Long id;
  private Long pointId;
  private int score;
  private String details;

  public void setPointId(Long pointId) {
    this.pointId = pointId;
  }
}
