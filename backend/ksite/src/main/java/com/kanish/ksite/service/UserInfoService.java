package com.kanish.ksite.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kanish.ksite.dto.FollowDto;
import com.kanish.ksite.dto.UserDto;
import com.kanish.ksite.dto.UserProfileDto;
import com.kanish.ksite.entity.Post;
import com.kanish.ksite.entity.UserInfo;
import com.kanish.ksite.helper.FileHelperUpload;
import com.kanish.ksite.repository.UserRepository;

@Service
public class UserInfoService {
	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder enc;

	@Autowired
	private FileHelperUpload fileHelper;

	public Boolean addUser(UserDto userDto) {
		try {
			UserInfo userInfo = new UserInfo();
			userInfo.setEmail(userDto.getEmail());
			userInfo.setFirstName(userDto.getFirstName());
			userInfo.setLastName(userDto.getLastName());
			userInfo.setRoles("user");
			userInfo.setMobile(userDto.getMobile());
			userInfo.setGender(userDto.getGender());
			userInfo.setPassword(userDto.getPassword());
			userInfo.setPassword(enc.encode(userInfo.getPassword()));
			repository.save(userInfo);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public UserInfo getById(long id) {
		Optional<UserInfo> userInfo = repository.findById(id);
		UserInfo user = userInfo.get();
		return user;
	}

	public UserInfo getByEmail(String email) {
		Optional<UserInfo> userInfo = repository.findByEmail(email);
		UserInfo user = userInfo.get();
		return user;
	}

	public Boolean update(UserInfo user) {
		if (!repository.existsById(user.getId()))
			return false;
		repository.save(user);
		return true;
	}

	public boolean addProfilePic(String fileName, String email) {

		try {
			UserInfo user = this.getByEmail(email);
			user.setProfile_pic(fileName);
			this.update(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean editProfilePic(String fileName, String email) {

		try {
			UserInfo user = this.getByEmail(email);
			String prev_fileName = user.getProfile_pic();
			fileHelper.deleteFile(prev_fileName);
			user.setProfile_pic(fileName);
			this.update(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateProfileInfo(UserDto userDto) {
		try {
			UserInfo userInfo = this.getByEmail(userDto.getEmail());
			userInfo.setFirstName(userDto.getFirstName());
			userInfo.setLastName(userDto.getLastName());
			userInfo.setGender(userDto.getGender());
			userInfo.setMobile(userDto.getMobile());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public UserDto getUserDto(String user_email) {
		
		
		UserInfo userInfo = this.getByEmail(user_email);
		UserDto userDto = new UserDto();
		userDto.setEmail(userInfo.getEmail());
		userDto.setFirstName(userInfo.getFirstName());
		userDto.setLastName(userInfo.getLastName());
		userDto.setGender(userInfo.getGender());
		userDto.setMobile(userInfo.getMobile());
		userDto.setImg_name(userInfo.getProfile_pic());
		
		return userDto;

	}

	public boolean followUser(FollowDto obj) {

		try {
			UserInfo loginUser = this.getByEmail(obj.getFollower_email());

			UserInfo target = this.getByEmail(obj.getFollowed_email());
			if(loginUser.getFollowing().contains(target)) return true;
			loginUser.getFollowing().add(target);
			this.update(loginUser);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<UserDto> getFollowing(String user_email) {
		UserInfo user = this.getByEmail(user_email);
		List<UserInfo> lst_following = user.getFollowing();
		
		List<UserDto> following = new ArrayList<>();
		for (UserInfo u : lst_following) {
			UserDto userInfo = new UserDto();
			userInfo.setEmail(u.getEmail());
			userInfo.setFirstName(u.getFirstName());
			userInfo.setLastName(u.getLastName());
			userInfo.setGender(u.getGender());
			following.add(userInfo);
		}
		return following;
	}

	public String unFollowUser(FollowDto followDto) {

		try {
			UserInfo loginUser = this.getByEmail(followDto.getFollower_email());

			UserInfo target = this.getByEmail(followDto.getFollowed_email());
			List<UserInfo> lst = loginUser.getFollowing();
			if (!lst.contains(target))
				return "not found";
			else {
				lst.remove(target);
				if (this.update(loginUser))
					return "updated";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

	public byte[] getProfilePic(String email) {

		try {
			UserInfo user = this.getByEmail(email);
			String fileName = user.getProfile_pic();
			return fileHelper.downloadFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Post> getPosts(String email) {

		try {
			UserInfo user = this.getByEmail(email);
			return user.getPosts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public boolean removePost(String email, Post post) {
		try {
			UserInfo user = this.getByEmail(email);
			List<Post> posts = user.getPosts();
			if (!posts.contains(post)) {
				return false;
			} else {
				posts.remove(post);
				this.update(user);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isFollower(UserInfo user, UserInfo user2) {
		return user.getFollowing().contains(user2);
	}

	public UserProfileDto getUserProfileDto(String user_email, String email) {
		UserProfileDto user=new UserProfileDto();
		UserInfo userInfo = this.getByEmail(user_email);
		UserDto userDto = new UserDto();
		userDto.setEmail(userInfo.getEmail());
		userDto.setFirstName(userInfo.getFirstName());
		userDto.setLastName(userInfo.getLastName());
		userDto.setGender(userInfo.getGender());
		userDto.setMobile(userInfo.getMobile());
		userDto.setImg_name(userInfo.getProfile_pic());
		userDto.setFolowing((long)userInfo.getFollowing().size());
		user.setUserDto(userDto);
		user.setFollow(this.getByEmail(email).getFollowing().contains(userInfo));
		
		return user;
	}

	public List<UserDto> getUsers(String email) {
		// TODO Auto-generated method stub
		UserInfo user=this.getByEmail(email);
		List<UserDto> users_dto=new ArrayList<UserDto>();
		List<UserInfo> users=user.getFollowing();
		List<UserInfo> users_all=repository.findAll();
		
		for (UserInfo u : users_all) {
			if(users.contains(u)) continue;
			UserDto userInfo = new UserDto();
			userInfo.setEmail(u.getEmail());
			userInfo.setFirstName(u.getFirstName());
			userInfo.setLastName(u.getLastName());
			userInfo.setGender(u.getGender());
			userInfo.setImg_name(u.getProfile_pic());
			users_dto.add(userInfo);
		}
		return users_dto;
	}
}
