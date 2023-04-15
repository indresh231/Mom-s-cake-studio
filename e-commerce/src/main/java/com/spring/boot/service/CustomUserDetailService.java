package com.spring.boot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.boot.model.CustomUserDetails;
import com.spring.boot.model.User;
import com.spring.boot.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService  {
	
	@Autowired
	UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		//fetching user from database
		Optional<User> user = userRepository.findUserByEmail(email);
		user.orElseThrow(()->new UsernameNotFoundException("User not found"));
		return user.map(CustomUserDetails::new).get();
	}

}
