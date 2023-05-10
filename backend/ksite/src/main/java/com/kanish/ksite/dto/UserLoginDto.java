package com.kanish.ksite.dto;

public class UserLoginDto {
	private UserDto userDto;
	
	private String token;

	public UserLoginDto() {
		super();
	}

	public UserLoginDto(UserDto userDto, String token) {
		super();
		this.userDto = userDto;
		this.token = token;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
