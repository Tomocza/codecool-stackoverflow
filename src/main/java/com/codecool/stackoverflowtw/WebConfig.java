package com.codecool.stackoverflowtw;

import com.codecool.stackoverflowtw.controller.dto.user.SessionDTO;
import com.codecool.stackoverflowtw.controller.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
  private final List<SessionDTO> activeSessions;
  
  @Autowired
  public WebConfig(List<SessionDTO> activeSessions) {
    this.activeSessions = activeSessions;
  }
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new AuthenticationInterceptor(activeSessions))
            .addPathPatterns("/answers/", "/answers/votes", "/questions/", "/questions/votes", "/users/logout")
            .excludePathPatterns("/");
  }
}
