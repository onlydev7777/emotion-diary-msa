package com.emotion.emotiondiarydiary.config;

import com.emotion.emotiondiarydiary.security.util.TokenUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@RequiredArgsConstructor
@Configuration
public class FeignConfig {

  @Bean
  public RequestInterceptor feignInterceptor() {
    return new AuthRequestInterceptor();
  }

  static class AuthRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
      template.header(HttpHeaders.AUTHORIZATION, TokenUtil.getToken());
    }
  }
}
