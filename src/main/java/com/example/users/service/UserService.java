package com.example.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.example.users.UserDto;
import com.example.users.model.UserRequestModel;


//need to implement spring framework's UserDetailsaService
public interface UserService extends UserDetailsService
{
	UserDto createUser(UserDto userDto);
	
	UserDto getUserDetailsByEmail(String email);

	UserDto getUserByUserId(String userId);

	
	UserDto getUserWithOutAlbums(String userId);
	
	void deleteUserByUserId(String userId);
	
	UserDto updateUserByUserId(String userId,UserRequestModel user);
}
