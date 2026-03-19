package com.example.guessnumberservice.repository;

import com.example.guessnumberservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findTop10ByOrderByScoreDesc();
    @Query("SELECT u FROM User u ORDER BY u.score DESC")
    Page<User> findTopUsers(Pageable pageable);
}
