package com.bigbell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.bigbell.jwt.JwtAccessDeniedHandler;
import com.bigbell.jwt.JwtAuthenticationEntryPoint;
import com.bigbell.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

/**
 * 원래 참고한 코드는 WebSecurityConfigurerAdapter을 상속받아 사용했지만 Spring Security가 2022년에 해당 기능을 deprecated했다.
 * 대신 HttpSecurity를 Configuring해서 사용하는 것으로 바뀜
 *
 */
@EnableWebSecurity //웹보안 활성화를위한 annotation
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * httpBasic().disable(): Http basic Auth  기반으로 로그인 인증창이 뜸.  disable 시에 인증창 뜨지 않음.
	 * .csrf().disable(): // rest api이므로 csrf(html tag를 통한 공격) 보안이 필요없으므로 disable처리.
	 * SessionCreationPolicy.STATELESS: // jwt token으로 인증하므로 stateless(스프링시큐리티가 생성하지도않고 기존것을 사용하지도 않음)하도록 처리.
	 * 
	 */
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic().disable()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 스프링 세션 정책
			.and()  // 시큐리티는 기본적으로 세션을 사용하지만 이 프로젝트에서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
				.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
			.and() // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
				.authorizeRequests()
				.antMatchers("/auth/**").permitAll()
				.anyRequest().authenticated()
			.and() // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
				.apply(new JwtSecurityConfig(tokenProvider));
		
		return http.build();
	}
}








