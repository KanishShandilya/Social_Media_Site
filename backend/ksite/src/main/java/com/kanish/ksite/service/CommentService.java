package com.kanish.ksite.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanish.ksite.dto.CommentDto;
import com.kanish.ksite.entity.Comment;
import com.kanish.ksite.entity.Post;
import com.kanish.ksite.entity.UserInfo;
import com.kanish.ksite.repository.CommentRepository;

@Service
public class CommentService {
	@Autowired
	CommentRepository commentRepository;
	
	public List<CommentDto> getComments(List<Comment> lst_comment){
		List<CommentDto> comments = new ArrayList<CommentDto>();
		for (Comment u : lst_comment) {
			CommentDto commentInfo = new CommentDto();
			commentInfo.setContent(u.getContent());
			commentInfo.setId(u.getId());
			commentInfo.setUser_email(u.getUser().getEmail());
			String name=u.getUser().getFirstName()+" "+u.getUser().getLastName();
			commentInfo.setName(name);
			comments.add(commentInfo);
		}
		return comments;
	}

	public boolean save(String content, UserInfo user, Post post) {
		try {
			Comment comment=new Comment();
			comment.setContent(content);
			comment.setPost(post);
			comment.setUser(user);
			commentRepository.save(comment);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
