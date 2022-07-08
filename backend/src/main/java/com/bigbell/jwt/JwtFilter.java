package com.bigbell.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{

	public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    
    private final TokenProvider tokenProvider; // 자동으로 의존성 주입됨
    
    // 실제 필터링 로직은 doFilterInternal 메소드에 포함
    // JWT 토큰의 인증 정보를 현재 thread의 SecurityContext에 저장하는 역할
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// 1. Request header에서 토큰을 꺼낸다.
		String jwt = resolveToken(request);
		
		// 2. validateToken으로 토큰 유효성 검사를 실시
		// 토큰이 정상이라면 해당 토큰으로 Authentication을 가져와 SecurityContext에 저장한다.
		if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			Authentication authentication = tokenProvider.getAuthentication(jwt);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		// Filter는 요청(Request)과 응답(Response)에 대한 정보들을 변경할 수 있게 개발자들에게 제공하는 서블릿 컨테이너이다.
		// FilterChain은 이런 Filter가 여러개 모여서 순서대로 하나의 체인을 형성한 것이다.
		filterChain.doFilter(request, response);
	}
	
	
	private String resolveToken(HttpServletRequest request) {
	
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(7);
		}
		
		return null;
	}
   
    
}
