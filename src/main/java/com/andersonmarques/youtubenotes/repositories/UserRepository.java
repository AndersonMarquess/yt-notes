package com.andersonmarques.youtubenotes.repositories;

import java.util.Optional;

import com.andersonmarques.youtubenotes.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
