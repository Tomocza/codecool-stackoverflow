package com.codecool.stackoverflowtw.controller.interceptor;

import com.codecool.stackoverflowtw.controller.dto.user.SessionDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
  private static final String SESSION_ID = "session_id";
  private static final String USER_ID = "user_id";
  private final Set<SessionDTO> activeSessions;
  
  @Autowired
  public AuthenticationInterceptor(Set<SessionDTO> activeSessions) {
    this.activeSessions = activeSessions;
  }
  
  @Override
  public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
          @NonNull Object handler) throws Exception {
    try {
      SessionDTO sessionDTO = getSessionDTO(request);
      response.addIntHeader(USER_ID, sessionDTO.user_id());
      response.addHeader(SESSION_ID, sessionDTO.session_id());
      return true;
    } catch (Exception e) {
      return false;
    }
  }
  
  private SessionDTO getSessionDTO(HttpServletRequest request) {
    Cookie sessionCookie = getSessionCookie(request);
    return getSessionDTO(sessionCookie);
  }
  
  private SessionDTO getSessionDTO(Cookie sessionCookie) {
    return activeSessions.stream().filter(filterSessionsBy(sessionCookie)).findFirst().orElseThrow();
  }
  
  private Cookie getSessionCookie(HttpServletRequest request) {
    return Arrays.stream(request.getCookies()).filter(this::getSessionIdCookie).findFirst().orElseThrow();
  }
  
  private Predicate<SessionDTO> filterSessionsBy(Cookie sessionCookie) {
    return session -> session.session_id().equalsIgnoreCase(sessionCookie.getValue());
  }
  
  private boolean getSessionIdCookie(Cookie cookie) {
    return cookie.getName().equalsIgnoreCase(SESSION_ID);
  }
}
