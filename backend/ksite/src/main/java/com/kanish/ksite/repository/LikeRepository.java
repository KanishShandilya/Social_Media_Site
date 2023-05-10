package com.kanish.ksite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanish.ksite.entity.Like;
import com.kanish.ksite.entity.Post;
import com.kanish.ksite.entity.UserInfo;

public interface LikeRepository extends JpaRepository<Like, Long> {
	Optional<Like> findByUserAndPost(UserInfo user,Post post);
}
