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
	
	private final MemberService memberService;
	
	@GetMapping("/me")
	public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
		MemberResponseDto myInfoBySecurity = memberService.getMyInfo();
        System.out.println(myInfoBySecurity.getNickname());
		return ResponseEntity.ok(memberService.getMyInfo());
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<MemberResponseDto> getMemberInfo(@PathVariable String email) {
		return ResponseEntity.ok(memberService.getMyInfoBySecurity(email));
	}
}
