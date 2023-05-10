package com.kanish.ksite.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kanish.ksite.dto.FollowDto;
import com.kanish.ksite.dto.UserDto;
import com.kanish.ksite.dto.UserProfileDto;
import com.kanish.ksite.helper.FileHelperUpload;
import com.kanish.ksite.service.UserInfoService;


//@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/profile")
public class ProfileController {
	
	/*
	 * follow user
	 * get following for a user
	 * edit profile info
	 * edit profile pic
	 * unfollow a user
	 * add profile pic
	 * get profile pic
	 * */
	
	@Autowired
	private UserInfoService userService;
	
	@Autowired
	private FileHelperUpload fileUpload;
	
	
	@PostMapping("/follow")
	public ResponseEntity<String> follow(@RequestBody FollowDto obj) {
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		obj.setFollower_email(email);
		if (userService.followUser(obj))
			return ResponseEntity.status(HttpStatus.OK).body("User Followed");
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");


	}
	
	@GetMapping("/following")
	public ResponseEntity<List<UserDto>> follow() {

		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		List<UserDto> following=userService.getFollowing(email);

		if(following.size()>0) return ResponseEntity.status(HttpStatus.OK).body(following);
		
		else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


	}

	
	@PutMapping
	public ResponseEntity<?> editProfileInfo(@RequestBody UserDto userDto){
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		userDto.setEmail(email);
		boolean b=userService.updateProfileInfo(userDto);
		if(b) {
			return ResponseEntity.ok("UserProfile Updated");
		}
		else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping("/profilePic")
	public ResponseEntity<?> editProfilePic(@RequestParam("file") MultipartFile file){
		String fileName="";
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		try {
			fileName=fileUpload.uploadFile(file);
			if(fileName.equals("failed")){
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
			}
			else {
				boolean b=userService.editProfilePic(fileName,email);
				if(!b)	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok("Uploaded Successfully");
	}
	
	@PostMapping("/unfollow")
	public ResponseEntity<String> unfollowUser(@RequestBody FollowDto followDto){
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		followDto.setFollower_email(email);
		String s=userService.unFollowUser(followDto);
		if(s.equals("not found")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
		}
		else if(s.equals("updated")){
			return ResponseEntity.status(HttpStatus.OK).body("User followed");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
	}
	
	@PostMapping("/profilePic")
	public ResponseEntity<String> addProfilePic(@RequestParam("image") MultipartFile file){
		String fileName="";
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		try {
			fileName=fileUpload.uploadFile(file);
			if(fileName.equals("failed")){
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
			}
			else {
				boolean b=userService.addProfilePic(fileName,email);
				if(!b)	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok("Uploaded Successfully");
	}
	@GetMapping("/profilePic/{email}")
	public ResponseEntity<?> getProfilePic(@PathVariable String email){
		
		byte[] image=userService.getProfilePic(email);
		if(image==null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.valueOf("image/png"))
					.body(image);
		}
	}
	
	@GetMapping("/{user_email}")
	public ResponseEntity<?> getUser(@PathVariable String user_email){
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		UserProfileDto user=userService.getUserProfileDto(user_email,email);
		if(user==null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(user);
		}
	}
	
	@GetMapping("/getUsers")
	public ResponseEntity<List<UserDto>> getUsers(){
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		List<UserDto> lst_users=userService.getUsers(email);
		return ResponseEntity.ok(lst_users);
	}
	
	@GetMapping("/getFullUser")
	public ResponseEntity<UserDto> getFullUser(){
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		return ResponseEntity.ok(userService.getUserDto(email));
	}
}
