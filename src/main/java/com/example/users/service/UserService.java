package com.example.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.example.users.UserDto;

@Component
public interface UserService extends UserDetailsService
{
	UserDto createUser(UserDto userDto);
	
	UserDto getUserDetailsByEmail(String email);

	UserDto getUserByUserId(String userId);

	
	UserDto getUserWithOutAlbums(String userId);
}
