package com.kanish.ksite.dto;

public class LikeDto {
	
	private long id;
	
	private String user_email;
	
	private long post_id;

	public LikeDto() {
		super();
	}

	public LikeDto(long id, String user_email, long post_id) {
		super();
		this.id = id;
		this.user_email = user_email;
		this.post_id = post_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
}
