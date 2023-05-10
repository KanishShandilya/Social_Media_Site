package com.kanish.ksite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanish.ksite.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
