package com.bigbell.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigbell.controller.dto.MemberResponseDto;
import com.bigbell.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {
	
	private final MemberService customerService;
	
	@GetMapping("/me")
	public ResponseEntity<MemberResponseDto> getMyCustomerInfo() {
		return ResponseEntity.ok(customerService.getMyInfo());
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<MemberResponseDto> getCustomerInfo(@PathVariable String email) {
		return ResponseEntity.ok(customerService.getCustomerInfo(email));
	}
}
