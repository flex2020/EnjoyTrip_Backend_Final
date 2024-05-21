package com.ssafy.enjoytrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ssafy.enjoytrip.security.JwtAuthenticationFilter;
import com.ssafy.enjoytrip.security.JwtProvider;
import com.ssafy.enjoytrip.security.OAuth2UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration // 이 클래스는 Spring의 설정 클래스로 사용
@EnableWebSecurity // Spring Security를 활성화합니다. 이 어노테이션은 웹 보안 기능을 추가하고, 기본 보안 설정을 적용
@RequiredArgsConstructor // final로 선언된 필드에 대한 생성자를 자동으로 생성. JwtProvider 주입을 위해 사용
public class SecurityConfig {
	// 이 친구는 JWT 토큰의 생성 및 검증을 담당
    private final JwtProvider jwtProvider;
    private final OAuth2UserServiceImpl oAuth2UserService;

    @Bean // 이 메소드가 반환하는 객체를 빈으로 등록. filterChain 메소드는 Spring Security의 주요 설정을 구성한다. HttpSecurity 객체로 다양한 보안 설정이 가능하다.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(httpSecurityHttpBasicConfigurer -> { // httpBasic은 기본인증 설정이다. 
                    httpSecurityHttpBasicConfigurer.disable(); // HTTP 기본 인증을 비활성화한다. 이는 브라우저의 기본 인증 팝업을 비활성화하는 데 사용된다.
                })
                .csrf(httpSecurityCsrfConfigurer -> { // CSRF(Cross-Site Request Forgery) 보호에 관한 설정이다.
                    httpSecurityCsrfConfigurer.disable(); // RESTful API를 사용하는 경우, 일반적으로 세션을 사용하지 않으므로 CSRF 보호를 비활성화한다.
                })
                .sessionManagement(httpSecuritySessionManagementConfigurer -> { // 세션 관리 설정이다.
                	// 세션을 사용하지 않도록 설정한다. 모든 요청은 상태 없이 처리되며, JWT를 통해 인증을 관리하도록 설정한다.
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> { // HTTP 요청에 대한 접근 권한을 설정한다.
                            authorizationManagerRequestMatcherRegistry
                            		.requestMatchers("/**").permitAll() // 모든 사용자 가능(개발중 혹은 공용 API에 적용)
//                            		.requestMatchers(HttpMethod.GET, "/files/**").permitAll()
//                                    .requestMatchers(HttpMethod.POST, "/login", "/signup").permitAll()
                                    // 관리자 로그인한 사용자만 가능
//                                    .requestMatchers(HttpMethod.GET).hasAnyRole("ADMIN")
//                                    .requestMatchers(HttpMethod.DELETE).hasAnyRole("ADMIN")
//                                    .requestMatchers(HttpMethod.POST).hasAnyRole("ADMIN")
//                                    .requestMatchers(HttpMethod.PUT).hasAnyRole("ADMIN")
                                    .anyRequest().authenticated(); // 나머지 모든 요청에 대해 인증을 요구
                })
                .oauth2Login(oauth2LoginConfigurer -> 
                	oauth2LoginConfigurer
                    	.userInfoEndpoint(userInfoEndpointConfigurer ->
                        	userInfoEndpointConfigurer
                            	.userService(oAuth2UserService)
                    	)
                    	.defaultSuccessUrl("/")
                    	.failureUrl("/login?error=true")
                )
        		// UsernamePasswordAuthenticationFilter(사용자 이름과 비밀번호를 통한 기본 인증 필터) 전에 JWT 인증을 처리하는 JwtAuthenticationFilter((user-defined) 필터 추가
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    /**
     * 비밀번호 암호화 알고리즘 설정
     * */
    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호를 암호화하는 PasswordEncoder 빈을 정의
    	// Crypt 해시 함수를 사용하여 비밀번호를 암호화하는 BCryptPasswordEncoder 객체를 반환한다. 이는 비밀번호를 안전하게 저장하고, 인증 시 비교하는 데 사용된다.
        return new BCryptPasswordEncoder();
    }
}