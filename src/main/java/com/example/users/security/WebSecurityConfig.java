package com.example.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.users.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	Environment environment;
	
    @Autowired
	
	BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.authorizeRequests().antMatchers("/users/**").permitAll().and().addFilter(getAuthenticationFilter());
      
		
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService,
				environment,authenticationManager());

		
		// for custom url instead of /login
		authenticationFilter.setFilterProcessesUrl("/users/login");

		return authenticationFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		
		// lets  spring framework know which service is used and which encoder is used.
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
		
	}
	
	

}
