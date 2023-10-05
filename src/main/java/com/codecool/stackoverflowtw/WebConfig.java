package com.codecool.stackoverflowtw;

import com.codecool.stackoverflowtw.controller.dto.user.SessionDTO;
import com.codecool.stackoverflowtw.controller.interceptor.AuthenticationInterceptor;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
            .addPathPatterns("/answers/", "/answers/votes/**", "/questions/", "/questions/votes/**", "/users/logout")
            .excludePathPatterns("/");
  }
  
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    Dotenv dotenv = Dotenv.load();
    registry.addMapping("/**")
            .allowedOrigins(dotenv.get("DOMAIN_NAME"))
            .allowedMethods("PUT", "DELETE", "GET", "POST", "PATCH")
            .allowCredentials(true)
            .maxAge(StackoverflowTwApplication.SESSION_EXPIRY_IN_SECONDS);
  }
}
