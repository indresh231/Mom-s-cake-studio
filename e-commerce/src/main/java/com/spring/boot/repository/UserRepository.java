package com.spring.boot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.boot.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional <User>findUserByEmail( String email);
}
