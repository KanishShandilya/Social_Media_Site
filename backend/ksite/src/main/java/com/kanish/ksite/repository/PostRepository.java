package com.kanish.ksite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanish.ksite.entity.Post;
import com.kanish.ksite.entity.UserInfo;

public interface PostRepository extends JpaRepository<Post, Long> {
	Optional<Post> findById(long id);
}
