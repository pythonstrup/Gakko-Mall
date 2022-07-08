package com.bigbell.jwt;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bigbell.controller.dto.TokenDto;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider {

	private static final String AUTHORITIES_KEY = "auth";
	private static final String BEARER_TYPE = "bearer";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
	private final Key key;
	
	public TokenProvider(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}
	
	// Authentication(인증) : 애플리케이션에서 사용자가 이 애플리케이션을 사용할 자격이 있는지 확인하는 과정
	// Authentication 객체 : Spring Security에서 한 유저의 인증 정보를 가지고 있는 객체, 사용자가 인증 과정을 성공적으로 마치면
	// Spring Security는 사용자의 정보 및 인증 성공여부를 가지고 Authentication 객체를 생성한 후 보관한다.
	public TokenDto generateTokenDto(Authentication authentication) {
		
		String authorities = authentication.getAuthorities().stream()
							.map(GrantedAuthority::getAuthority)
							.collect(Collectors.joining(""));
		
		long now = (new Date()).getTime();
		
		// Access Token 만들기
		Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
		String accessToken = Jwts.builder()
                .setSubject(authentication.getName())           // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn)              // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)     // header "alg": "HS512"
                .compact();
		
		String refreshToken = Jwts.builder()
				.setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
		
		return TokenDto.builder()
				.grantType(BEARER_TYPE)
				.accessToken(accessToken)
				.accessTokenExpiresIn(accessTokenExpiresIn.getTime())
				.refreshToken(refreshToken)
				.build();
	}
	
	public Authentication getAuthentication(String accessToken) {
		// 토큰을 복호화한다.
		Claims claims = parseClaims(accessToken);
		
		if (claims.get(AUTHORITIES_KEY) == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}
		
		// 클레임에서 권한 정보를 가져온다.
		Collection<? extends GrantedAuthority> authorities =
						Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
								.map(SimpleGrantedAuthority::new)
								.collect(Collectors.toList());
		
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}
	
	// 토큰의 유효성 검증 메소드
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch(SecurityException | MalformedJwtException e) {
			log.info("잘못된 JWT 서명입니다.");
		} catch(ExpiredJwtException e) {
			log.info("만료된 JWT 토큰입니다.");
		} catch(UnsupportedJwtException e) {
			log.info("지원되지 않는 JWT 토큰입니다.");
		} catch(IllegalArgumentException e) {
			log.info("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}
	
	private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().
            		setSigningKey(key).
            		build().
            		parseClaimsJws(accessToken).
            		getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
