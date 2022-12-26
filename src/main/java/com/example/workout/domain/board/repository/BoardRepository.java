package com.example.workout.domain.board.repository;


import com.example.workout.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board , Long> {
}
