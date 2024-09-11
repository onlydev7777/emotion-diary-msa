package com.emotion.emotiondiarydiary.api;

import com.emotion.emotiondiarydiary.api.response.PointResponse;
import com.emotion.emotiondiarydiary.error.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ediary-point")
public interface PointServiceClient {

  @GetMapping("/{memberId}")
  ApiResult<PointResponse> getPointByMemberId(@PathVariable Long memberId);
}
