package com.hn004.reddit.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hn004.reddit.Security.JwtAuthenticationFilter;
import com.hn004.reddit.mapper.PostMapper;
//import com.hn004.reddit.mapper.PostMapperImpl;
import com.hn004.reddit.mapper.SubredditMapper;
//import com.hn004.reddit.mapper.SubredditMapperImpl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("deprecation")
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityCongfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private  UserDetailsService userDetailsService;
	
//	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception{
		
		httpSecurity.csrf().disable()
		.authorizeRequests()
		.antMatchers("/api/auth/**")
		.permitAll()
		.antMatchers(HttpMethod.GET,"/api/subreddit")
		.permitAll()
		.antMatchers(HttpMethod.GET, "/api/posts/")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/api/posts/**")
        .permitAll()
		.anyRequest()
		.authenticated();
		
		httpSecurity.addFilterBefore(jwtAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
        
	}
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoader());
	}
	
	@Bean
	PasswordEncoder passwordEncoader() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
	
//	@Bean
//	SubredditMapperImpl subredditMapper() {
//		return new SubredditMapperImpl();
//	}
//	
//	@Bean
//	PostMapperImpl postMapper() {
//		return new PostMapperImpl();
//	}
//	


}
