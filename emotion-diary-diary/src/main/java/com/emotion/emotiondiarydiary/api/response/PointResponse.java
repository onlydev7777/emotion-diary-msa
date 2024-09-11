package com.emotion.emotiondiarydiary.api.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointResponse {

  private Long id;
  private Long memberId;
  private int score;
  private List<PointHistoryResponse> historyResponses;
}
