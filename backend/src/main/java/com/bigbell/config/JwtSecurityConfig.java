package com.bigbell.config;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bigbell.jwt.JwtFilter;
import com.bigbell.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

// 직접 생성한 TokenProvider와 JwtFilter를 SecurityConfig에 적용하기 위한 클래스
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	
	private final TokenProvider tokenProvider;

	// 의존성 주입된 tokenProvider 인스턴스와 JwtFilter를 통해 Security 로직에 필터를 등록한다.
	@Override  // SecurityConfigurerAdapter가 가지고 있는 메소드
	public void configure(HttpSecurity builder) throws Exception {
		JwtFilter customFilter = new JwtFilter(tokenProvider);
		builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
}
