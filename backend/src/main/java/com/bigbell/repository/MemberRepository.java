package com.bigbell.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigbell.domain.user.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);
	boolean existsByEmail(String email);
}
