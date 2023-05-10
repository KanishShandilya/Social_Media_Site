package com.kanish.ksite.dto;

public class UserDto {
	
	private long id;
	
	private String firstName;
	
	private String lastName;

	private String gender;
	
	private String mobile;
	
	private String email;
	
	private String password;
	
	private String img_name;
	
	private Long folowing;
	
	private String old_password;
	
	private String new_password;
	public UserDto() {
		super();
	}

	public UserDto(String firstName, String lastName, String gender, String mobile, String email, String password,long id,String img_name) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.mobile = mobile;
		this.email = email;
		this.password = password;
		this.id=id;
		this.img_name=img_name;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImg_name() {
		return img_name;
	}

	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}

	public Long getFolowing() {
		return folowing;
	}

	public void setFolowing(Long folowing) {
		this.folowing = folowing;
	}

	public String getOld_password() {
		return old_password;
	}

	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}
	
}
