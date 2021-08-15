package com.example.users.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.users.UserDto;
import com.example.users.entity.UserEntity;
import com.example.users.model.UserAlbumResponseModel;
import com.example.users.model.UserRequestModel;
import com.example.users.model.UserResponseModel;
import com.example.users.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(path = "/users")
public class UserController
{
	
  @Autowired 
  Environment env;
  
  @Autowired
  UserService userService;
  
  @Autowired
  ModelMapper modelMapper;
  
  
  @PostConstruct
  public void modelMapperConfigurationSetup() {
	 
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	   
  }
  
   @GetMapping
  public String  getUser(@RequestParam(value ="page", defaultValue = "1") int page,@RequestParam(value="limit",defaultValue = "50") int limit
		  ,@RequestParam(value = "sort",required = false) String sort)
	{
		return "page= "+page+" and limit= "+limit;
		
	}
 
   
   @GetMapping(path="/{userId}",produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@CircuitBreaker(name = "albumBreaker",fallbackMethod="albumFallBack")
	
	public ResponseEntity<UserAlbumResponseModel> getUser(@PathVariable(value ="userId") String userId)
	{
		
		
	
		UserDto userDto= userService.getUserByUserId(userId);
		
		UserAlbumResponseModel userAlbumModel=modelMapper.map(userDto, UserAlbumResponseModel.class);
		return ResponseEntity.status(HttpStatus.OK).body( userAlbumModel);
		
		
		
	}
	
	 public ResponseEntity<UserAlbumResponseModel> albumFallBack(@PathVariable(value ="userId") String userId,Exception e)
	{
		 
			
		
			UserDto userDto= userService.getUserWithOutAlbums(userId);
			UserAlbumResponseModel userAlbumModel=modelMapper.map(userDto, UserAlbumResponseModel.class);
			return ResponseEntity.status(HttpStatus.OK).body( userAlbumModel);
		
	}
	 
	 
  
	@GetMapping(path="/status/check")
	public String status() {
		
		return "working on port "+ env.getProperty("local.server.port") + " "+ env.getProperty("token.secret");
	}
	
	@PostMapping(consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}
	,produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserRequestModel user)
	{
		
		
		
		UserDto userDto = modelMapper.map(user, UserDto.class);
		userDto.toString();
		UserDto createdDto=userService.createUser(userDto);
		
		
		
		UserResponseModel response = modelMapper.map(createdDto, UserResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
		
		
	}
	
	
	 
	 @DeleteMapping(path = "/{userId}")
	 public ResponseEntity<Void> deleteUser(@PathVariable(value ="userId") String userId)
	 {
		 
		 userService.deleteUserByUserId(userId);
		 
		
		//return ResponseEntity.noContent().build();
		 return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	 }
	 
	 //For updating user details
	 
	 @PutMapping(path="/{userId}",consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}
		,produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	 public ResponseEntity<UserResponseModel> updateUser(@PathVariable(value ="userId")  String userId,@RequestBody UserRequestModel user)
	 {
		 
	    UserDto updatedDto=userService.updateUserByUserId(userId, user);
		 
	    UserResponseModel response =  modelMapper.map(updatedDto, UserResponseModel.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	 }
	 
	
}
