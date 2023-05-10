package com.kanish.ksite.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.kanish.ksite.entity.UserInfo;
import com.kanish.ksite.repository.UserRepository;

@Component
public class UserInfoUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserInfo> userInfo=repository.findByEmail(username);
		return userInfo.map(UserInfoUserDetails::new)
		.orElseThrow(()->new UsernameNotFoundException("User Not Found"));
		
	}

}
