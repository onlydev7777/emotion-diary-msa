package com.gateway.gatewayservice.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

  public LoggingFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return new OrderedGatewayFilter((exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();

      if (config.isPreLogger()) {
        log.info("Logging Pre Filter : request id -> {}", request.getId());
      }

      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        if (config.isPostLogger()) {
          log.info("Logging Post Filter : response code -> {}", response.getStatusCode());
        }
      }));
    }, Ordered.HIGHEST_PRECEDENCE);
  }

  @Setter
  @Getter
  public static class Config {

    private boolean preLogger;
    private boolean postLogger;
  }
}
