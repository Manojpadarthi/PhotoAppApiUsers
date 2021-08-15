package com.example.users;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
public class PhotoAppApiUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiUsersApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {

		return new RestTemplate();

	}
	
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}

	/*
	 * @Bean Logger.Level getLogger() { return Logger.Level.FULL; }
	 * 
	 * @Bean public FeignDecoder getFeignDecoder() { return new FeignDecoder(); }
	 */

}
