package com.emotion.emotiondiarydiary.api;

import com.emotion.emotiondiarydiary.api.response.MemberResponse;
import com.emotion.emotiondiarydiary.error.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {

  @GetMapping("/member/{id}")
  ApiResult<MemberResponse> getMember(@PathVariable Long id);
}
