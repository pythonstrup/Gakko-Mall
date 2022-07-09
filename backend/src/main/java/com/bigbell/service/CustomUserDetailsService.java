package com.bigbell.service;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigbell.domain.user.Member;
import com.bigbell.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final MemberRepository memberRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return memberRepository.findByEmail(username)
				.map(this::createUserDetails)
				.orElseThrow(() -> new UsernameNotFoundException(username + "를 데이터베이스에서 찾을 수 없습니다."));
	}
	
	private UserDetails createUserDetails(Member user) {
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());
		
		return new User(
				String.valueOf(user.getId()),
				user.getPassword(),
				Collections.singleton(grantedAuthority)
			);
				
	}
}






