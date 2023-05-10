package com.kanish.ksite.dto;

import java.util.List;

public class PostUserDto {
	private List<PostDto> lst_posts;
	private List<Boolean> likes;
	
	public PostUserDto() {
		super();
	}
	public PostUserDto(List<PostDto> lst_posts, List<Boolean> likes) {
		super();
		this.lst_posts = lst_posts;
		this.likes = likes;
	}
	public List<PostDto> getLst_posts() {
		return lst_posts;
	}
	public void setLst_posts(List<PostDto> lst_posts) {
		this.lst_posts = lst_posts;
	}
	public List<Boolean> getLikes() {
		return likes;
	}
	public void setLikes(List<Boolean> likes) {
		this.likes = likes;
	}	
}
