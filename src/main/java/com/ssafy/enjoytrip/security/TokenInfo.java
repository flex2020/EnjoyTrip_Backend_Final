package com.ssafy.enjoytrip.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TokenInfo {
  private String grantType;
  private String accessToken;
  private String refreshToken;
}
