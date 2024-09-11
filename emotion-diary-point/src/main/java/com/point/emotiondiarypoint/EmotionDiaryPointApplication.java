package com.point.emotiondiarypoint;

import com.point.emotiondiarypoint.security.jwt.JwtProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableConfigurationProperties(value = {JwtProvider.class})
@EnableDiscoveryClient
@SpringBootApplication
public class EmotionDiaryPointApplication {

  public static void main(String[] args) {
    SpringApplication.run(EmotionDiaryPointApplication.class, args);
  }

}
