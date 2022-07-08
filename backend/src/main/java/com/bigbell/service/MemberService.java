package com.bigbell.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bigbell.controller.dto.MemberResponseDto;
import com.bigbell.repository.MemberRepository;
import com.bigbell.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {
	
	private final MemberRepository customerRepository;
	
	@Transactional(readOnly = true)
	public MemberResponseDto getCustomerInfo(String email) {
		return customerRepository.findByEmail(email)
						.map(MemberResponseDto::of)
						.orElseThrow(() -> new RuntimeException("해당 유저 정보가 없습니다."));
	}
	
	@Transactional(readOnly = true)
	public MemberResponseDto getMyInfo() {
		return customerRepository.findById(SecurityUtil.getCurrentCustomerId())
						.map(MemberResponseDto::of)
						.orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
	}
}
