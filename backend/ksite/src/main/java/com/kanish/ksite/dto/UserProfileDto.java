package com.kanish.ksite.dto;

public class UserProfileDto {
	private UserDto userDto;
	private boolean follow;
	public UserProfileDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserProfileDto(UserDto userDto, boolean follow) {
		super();
		this.userDto = userDto;
		this.follow = follow;
	}
	public UserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	public boolean isFollow() {
		return follow;
	}
	public void setFollow(boolean follow) {
		this.follow = follow;
	}
	
	
}
