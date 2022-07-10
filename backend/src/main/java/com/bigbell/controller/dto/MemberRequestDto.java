package com.bigbell.controller.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bigbell.domain.user.Authority;
import com.bigbell.domain.user.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {
	
	private String email;
	private String password;
	private String nickname;
	
	public Member toMember(PasswordEncoder passwordEncoder) {
		return Member.builder()
						.email(email)
						.password(passwordEncoder.encode(password))
						.nickname(nickname)
						.authority(Authority.ROLE_CUSTOMER)
						.build();
	}
	
	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}
}
