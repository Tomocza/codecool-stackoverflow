package com.codecool.stackoverflowtw;

import com.codecool.stackoverflowtw.controller.dto.user.SessionDTO;
import com.codecool.stackoverflowtw.controller.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
  private final Set<SessionDTO> activeSessions;
  
  @Autowired
  public WebConfig(Set<SessionDTO> activeSessions) {
    this.activeSessions = activeSessions;
  }
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new AuthenticationInterceptor(activeSessions))
            .addPathPatterns("/answers/", "/questions/", "/users/logout")
            .excludePathPatterns("/");
  }
}
