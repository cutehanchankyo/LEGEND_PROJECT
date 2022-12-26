package com.example.workout.domain.email.repository;

import com.example.workout.domain.email.entity.EmailAuth;
import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRepository extends CrudRepository<EmailAuth, String> {
}
