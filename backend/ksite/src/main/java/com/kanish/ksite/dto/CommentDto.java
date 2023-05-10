package com.kanish.ksite.dto;

public class CommentDto {
	
	private long id;
	
	private String content;
	
	private String user_email;
	private String name;
	private long post_id;

	public CommentDto() {
		super();
	}

	public CommentDto(long id, String content, String user_email, long post_id,String name) {
		super();
		this.id = id;
		this.content = content;
		this.user_email = user_email;
		this.post_id = post_id;
		this.name=name;
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

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public long getPost_id() {
		return post_id;
	}

	public void setPost_id(long post_id) {
		this.post_id = post_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
