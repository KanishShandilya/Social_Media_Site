package com.kanish.ksite.entity;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "user",uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String gender;

	private String mobile;
	
	private String email;

	private String password;

	private String firstName;

	private String lastName;

	private String roles;
	
	private String profile_pic;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "relation",
	           joinColumns = @JoinColumn(name = "id"),
	           inverseJoinColumns = @JoinColumn(name = "following_id"))
	private List<UserInfo> following;
	
	@OneToMany(mappedBy = "user")
	private List<Post> posts;

	public List<Post> getPosts() {
		return posts;
	}


	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}


	public UserInfo() {
		super();
	}
	

	public UserInfo(long id, String gender, String mobile, String email, String password, String firstName,
			String lastName, String roles, String profile_pic, List<UserInfo> following,List<Post> posts) {
		super();
		this.id = id;
		this.gender = gender;
		this.mobile = mobile;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = roles;
		this.profile_pic = profile_pic;
		this.following = following;
		this.posts=posts;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public List<UserInfo> getFollowing() {
		return following;
	}

	public void setFollowing(List<UserInfo> following) {
		this.following = following;
	}


	public String getProfile_pic() {
		return profile_pic;
	}


	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
	}


	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", gender=" + gender + ", mobile=" + mobile + ", email=" + email + ", password="
				+ password + ", firstName=" + firstName + ", lastName=" + lastName + ", roles=" + roles
				+ ", profile_pic=" + profile_pic + ", following=" + following + ", posts=" + posts + "]";
	}

	
}
