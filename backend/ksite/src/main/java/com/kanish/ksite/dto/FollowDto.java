package com.kanish.ksite.dto;

public class FollowDto {
	
	private String follower_email;
	
	private String followed_email;

	public FollowDto() {
		super();
	}

	public FollowDto(String follower_email, String followed_email) {
		super();
		this.follower_email = follower_email;
		this.followed_email = followed_email;
	}

	public String getFollower_email() {
		return follower_email;
	}

	public void setFollower_email(String follower_email) {
		this.follower_email = follower_email;
	}

	public String getFollowed_email() {
		return followed_email;
	}

	public void setFollowed_email(String followed_email) {
		this.followed_email = followed_email;
	}

	
}
