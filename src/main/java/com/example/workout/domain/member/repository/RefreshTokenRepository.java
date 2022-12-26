package com.example.workout.domain.member.repository;

import com.example.workout.domain.member.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findRefreshTokenByEmail(String email);
}
