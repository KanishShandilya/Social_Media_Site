package com.kanish.ksite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanish.ksite.entity.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Long> {

	Optional<UserInfo> findByEmail(String username);
	Optional<UserInfo> findById(long id);
	boolean existsByEmail(String email);
}
