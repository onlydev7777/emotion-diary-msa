package com.emotion.emotiondiarydiary.config;

import io.github.resilience4j.core.ContextAwareScheduledThreadPoolExecutor;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * https://docs.spring.io/spring-cloud-circuitbreaker/reference/spring-cloud-circuitbreaker-resilience4j/circuit-breaker-properties-configuration.html
 */
@Configuration
public class Resilience4JConfig {

  @Bean
  public ContextAwareScheduledThreadPoolExecutor contextAwareScheduledThreadPool() {
    return ContextAwareScheduledThreadPoolExecutor.newScheduledThreadPool()
        .corePoolSize(8)
        .contextPropagators(new SecurityContextPropagator()).build();
  }

  @Bean
  public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration(ContextAwareScheduledThreadPoolExecutor executor) {
//    CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
//        .failureRateThreshold(4)  //실패 임계치 % 설정
//        .waitDurationInOpenState(Duration.ofSeconds(4)) //써킷 open 지속 시간
//        .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)  //카운트 or 시간 기반으로 설정할 수 있음
//        .slidingWindowSize(2)   // 카운트일 경우 사이즈 만큼의 마지막 요청 횟수로 실패 임계치 계산, 시간일 경우 사이즈 만큼의 seconds 로 call 집계해서 실패 임계치 계산
//        .build();
//
//    TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
//        .timeoutDuration(Duration.ofSeconds(4)) //supplier 서비스(auth-service)의 타임아웃 오류 발생 기준 seconds
//        .build();

//    return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//        .timeLimiterConfig(timeLimiterConfig)
//        .circuitBreakerConfig(circuitBreakerConfig)
//        .build()
//    );

    return factory -> {
//      factory.configureGroupExecutorService(
//          group -> new DelegatingSecurityContextExecutorService(Executors.newVirtualThreadPerTaskExecutor()));
      factory.configureGroupExecutorService(group -> executor);
      factory.configureExecutorService(executor);
    };
  }
}
