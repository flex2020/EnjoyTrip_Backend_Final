package com.ssafy.enjoytrip.security;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ssafy.enjoytrip.member.model.MemberDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@PropertySource(value="classpath:/securityvalue.properties") // application-secret.properties 파일에서 프로퍼티 값을 로드
public class JwtProvider {
    private final Key key; // JWT 서명을 위한 키

    // 생성자에서 주입된 secretKey로 JWT 서명을 위한 키인 key를 초기화한다.
    public JwtProvider(@Value("${jwt.secretkey}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 사용자의 정보를 포함한 JWT 토큰 생성 메소드
    // authentication은 Spring Security의 인증 객체로, 현재 인증된 사용자에 대한 정보를 포함한다.
    public TokenInfo generateToken(Authentication authentication, MemberDto member) {
    	// 사용자의 권한을 콤마(,)로 구분된 문자열로 변환한다.(여러개의 권한이 있을수도 있잖아요)
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        // 액세스 토큰의 만료 시간을 설정한다. 일단 현재 시간으로부터 3시간 후로 설정한다.
        Date accessTokenExpiresIn = new Date((new Date()).getTime() + 1000L * 60 * 60 * 3);

        // JWT 액세스 토큰을 생성한다.
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // 토큰의 주체(Subject)를 설정
                .claim("memberId", member.getMemberId()) // 토큰에 사용자 ID를 클레임으로 추가
                .claim("nickname", member.getNickname())
                .claim("auth", authorities) // 토큰에 권한 정보를 클레임으로 추가
                .setExpiration(accessTokenExpiresIn) // 토큰의 만료 시간을 설정
                .signWith(key, SignatureAlgorithm.HS256) // HMAC SHA-256 알고리즘을 사용하여 서명
                .compact();

        // JWT 리프레시 토큰을 생성한다.(expire 시간은 일단 액세스랑 동일하게 설정(이럼 왜씀?))
        String refreshToken = Jwts.builder()
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // JWT 토큰 정보를 포함하는 데이터 클래스인 TokenInfo(user-defined)를 만들어서 반환한다.
        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT 토큰을 파싱하여 인증 정보를 가져오는 메소드다.
    // accessToken은 클라이언트가 전송한 JWT 액세스 토큰이다.
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken); // JWT를 파싱하여 클레임 정보를 가져온다.
        Collection<? extends GrantedAuthority> authorities = new ArrayList(); // 사용자 권한을 저장할 컬렉션이다.
        // UserDetails 객체를 생성한다. 사용자의 이름은 클레임의 주체(Subject)에서 가져오고, 비밀번호와 권한은 빈 값으로 설정한다.
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        // 이걸로 UsernamePasswordAuthenticationToken 객체를 생성하여 반환한다. 이 객체는 Spring Security에서 인증된 사용자 정보를 나타낸다.
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // JWT 토큰의 유효성을 검증하는 메소드
    // token은 클라이언트가 전송한 JWT이다.
    public boolean validateToken(String token) {
        try {
        	// JWT를 파싱하여 유효성을 검증한다. 서명을 확인하고, 클레임을 파싱한다.
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) { // 잘못된 JWT 서명인 경우
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) { // 만료된 JWT인 경우
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) { // 지원되지 않는 JWT인 경우
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) { // JWT 클레임 문자열이 비어있는 경우
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    // JWT 토큰의 클레임을 파싱하는 메소드
    private Claims parseClaims(String accessToken) {
        try {
        	// JWT를 파싱하여 클레임(Claims) 정보를 추출한다.
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) { // 토큰이 만료된 경우
            return e.getClaims();
        }
    }
}
