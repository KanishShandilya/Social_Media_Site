package com.kanish.ksite.dto;



public class FullPostDto {
	private PostDto post;
	private Boolean like;
	
	
	public FullPostDto() {
		super();
	}
	public FullPostDto(PostDto post, Boolean like) {
		super();
		this.post = post;
		this.like = like;
	}
	public PostDto getPost() {
		return post;
	}
	public void setPost(PostDto post) {
		this.post = post;
	}
	public Boolean getLike() {
		return like;
	}
	public void setLike(Boolean like) {
		this.like = like;
	}
	
	
}
