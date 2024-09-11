package com.point.emotiondiarypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(SecurityConfig.class)
public abstract class ControllerTestSupport {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected WebApplicationContext context;

  @Autowired
  protected ObjectMapper objectMapper;
}
