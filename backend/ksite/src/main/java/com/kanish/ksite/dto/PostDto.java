package com.kanish.ksite.dto;

import java.util.Date;
import java.util.List;

public class PostDto {
	
	private long id;
	
	private String content;

	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private String img_name;
	
	private Date dateCreated;
	
	private long n_likes;
	
	private long n_comments;
	
	private List<CommentDto> comments;
	
	private List<LikeDto> likes;
	
	public PostDto() {
		super();
	}

	

	public PostDto(long id, String content, String email, String firstName, String lastName, String img_name,
			Date dateCreated, long n_likes, long n_comments, List<CommentDto> comments, List<LikeDto> likes) {
		super();
		this.id = id;
		this.content = content;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.img_name = img_name;
		this.dateCreated = dateCreated;
		this.n_likes = n_likes;
		this.n_comments = n_comments;
		this.comments = comments;
		this.likes = likes;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImg_name() {
		return img_name;
	}

	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<CommentDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}

	public List<LikeDto> getLikes() {
		return likes;
	}

	public void setLikes(List<LikeDto> likes) {
		this.likes = likes;
	}

	public long getN_likes() {
		return n_likes;
	}

	public void setN_likes(long n_likes) {
		this.n_likes = n_likes;
	}

	public long getN_comments() {
		return n_comments;
	}

	public void setN_comments(long n_comments) {
		this.n_comments = n_comments;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
