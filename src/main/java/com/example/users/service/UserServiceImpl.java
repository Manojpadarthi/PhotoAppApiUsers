package com.example.users.service;

//import com.example.users.AlbumsServiceClient;
import com.example.users.UserDto;
import com.example.users.entity.UserEntity;
import com.example.users.model.AlbumResponseModel;
import com.example.users.model.UserRequestModel;
import com.example.users.repository.UsersRepository;



import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

//import javax.ws.rs.HttpMethod;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
//import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService 
{
	
	org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UsersRepository repo;
	
	@Autowired
	
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	RestTemplate restTemplate;
	
	/*@Autowired
	AlbumsServiceClient client;*/
	
	@Autowired
	Environment env;
	
	
	
	

	@PostConstruct
   public void modelMapperConfigurationSetup() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	   
   }
	
	
	
	@Override
	public UserDto createUser(UserDto userDetails) {
		
		userDetails.setUserId(UUID.randomUUID().toString());
		
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		// TODO Auto-generated method stub
		
		
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		
		UserEntity savedEntity=repo.save(userEntity);
		UserDto dtoReturn = modelMapper.map(savedEntity, UserDto.class);
		
		return dtoReturn;
	}

	//when spring frame work is trying to authenticate the user 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		
		UserEntity userEntity=repo.findByEmail(username);
		if(userEntity==null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),true,true,true,true,
				new ArrayList<>());
	}


	@Override
	public UserDto getUserDetailsByEmail(String email) {
		
		UserEntity userEntity=repo.findByEmail(email);
		if(userEntity==null) {
			throw new UsernameNotFoundException(email);
		}
		
		
		return modelMapper.map(userEntity, UserDto.class);
	}


	@Override
	public UserDto getUserByUserId(String userId)
	{
		UserEntity userEntity = repo.findByUserId(userId);
		if(userEntity==null ) {
			throw new UsernameNotFoundException(userId);
		}
		String albumUrl = String.format(env.getProperty("albums.uri"),userId);
		
		logger.info("before calling albums service");
		
		ResponseEntity<List<AlbumResponseModel>> albumList = restTemplate.exchange(albumUrl,HttpMethod.GET, null, new ParameterizedTypeReference
				<List<AlbumResponseModel>>() {});
		List<AlbumResponseModel> albums = albumList.getBody();
		//List<AlbumResponseModel> albums = client.getAlbums(userId);
		logger.info("after calling albums micro services");
		UserDto userDto=modelMapper.map(userEntity, UserDto.class);
		
		userDto.setAlbums(albums);
		
		return userDto;
	}


	@Override
	public UserDto getUserWithOutAlbums(String userId) {
		
		UserEntity userEntity = repo.findByUserId(userId);
		if(userEntity==null ) {
			throw new UsernameNotFoundException(userId);
		}
		List<AlbumResponseModel> albums = new ArrayList<AlbumResponseModel>();
		
		UserDto userDto=modelMapper.map(userEntity, UserDto.class);
		userDto.setAlbums(albums);
		
		return userDto;
	}

    @Transactional
	@Override
	public void deleteUserByUserId(String userId) 
	{
	    repo.deleteByUserId(userId);
		
	}

    

	@Override
	public UserDto updateUserByUserId(String userId,UserRequestModel user) {
		UserEntity fetchedUser=repo.findByUserId(userId);
		fetchedUser.setFirstName(user.getFirstName());
		fetchedUser.setLastName(user.getLastName());
		UserEntity entity=repo.save(fetchedUser);
		
		UserDto userDto=modelMapper.map(entity, UserDto.class);
		return userDto;
	}
	
	
	
	
	

}
