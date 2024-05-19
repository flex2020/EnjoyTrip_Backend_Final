package com.ssafy.enjoytrip.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean { // JWT 토큰을 추출하고, 이를 검증하여 인증 정보를 설정한다.
  // JWT 토큰의 생성 및 검증을 담당하는 클래스다.
  private final JwtProvider jwtProvider;
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      /**
       * Request Header 에서 JWT 추출
       * */
      String token = resolveToken((HttpServletRequest) request);

      /**
       * validateToken 으로 토큰 유효성 검사
       * */
      if (token != null && jwtProvider.validateToken(token)) {
          // 토큰이 유효할 경우
          // 토큰에서 Authentication 객체를 가져와 SecurityContext 에 저장
          Authentication authentication = jwtProvider.getAuthentication(token);
          // 생성된 인증 객체를 SecurityContext에 설정한다. 이를 통해 현재 요청은 인증된 사용자로 처리된다.
          SecurityContextHolder.getContext().setAuthentication(authentication);
      }
      chain.doFilter(request, response); // 필터 체인의 다음 필터를 호출하여 요청을 처리한다.
  }


  /**
   * Request Header 에서 토큰 정보를 추출하는 함수
   * */
  private String resolveToken(HttpServletRequest request) {
	  // request.getHeader("Authorization") 요청 헤더에서 Authorization 헤더의 값을 가져온다. 이 헤더는 일반적으로 "Bearer {JWT}" 형식으로 JWT를 포함한다.
      String bearerToken = request.getHeader("Authorization");
      // hasText로 bearerToken이 비어 있지 않은지 확인 && bearerToken이 "Bearer "로 시작하는지 확인한다.
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
    	  // "Bearer " 문자열 이후의 부분을 반환합니다. 즉, 실제 JWT를 반환한다.
          return bearerToken.substring(7);
      }
      return null;
  }
}
