package com.bigbell.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bigbell.domain.token.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByKey(String key);
}
