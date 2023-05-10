package com.kanish.ksite.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kanish.ksite.dto.AuthRequest;
import com.kanish.ksite.dto.UserDto;
import com.kanish.ksite.dto.UserLoginDto;
import com.kanish.ksite.service.JwtService;
import com.kanish.ksite.service.UserInfoService;

//@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/auth")
public class UserController {
	
	/*
	 * sign up
	 * login
	 */
	
	@Autowired
	private UserInfoService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/signup")
	public ResponseEntity<?> addNewUser(@RequestBody UserDto userInfo) {
		Boolean op= userService.addUser(userInfo);
		if(op) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserLoginDto> authenticateAndGetToken(@RequestBody AuthRequest authrequest) {
		Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authrequest.getEmail(), authrequest.getPassword()));
		if(authentication.isAuthenticated()) {
			String token=jwtService.generateToken(authrequest.getEmail());
			UserDto userDto=userService.getUserDto(authrequest.getEmail());
			UserLoginDto userLoginDto=new UserLoginDto();
			userLoginDto.setToken(token);
			userLoginDto.setUserDto(userDto);
			return ResponseEntity.ok(userLoginDto);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}
}
