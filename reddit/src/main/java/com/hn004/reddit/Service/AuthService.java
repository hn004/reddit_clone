package com.hn004.reddit.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hn004.reddit.Entity.NotificationEmail;
import com.hn004.reddit.Entity.User;
import com.hn004.reddit.Entity.VerificationToken;
import com.hn004.reddit.Repository.UserRepo;
import com.hn004.reddit.Repository.VerificationTokenRepo;
import com.hn004.reddit.Security.JwtProvider;
import com.hn004.reddit.Service.CustomException.StringRedditException;
import com.hn004.reddit.dto.AuthenticationResponse;
import com.hn004.reddit.dto.LoginRequest;
import com.hn004.reddit.dto.RegisterRequest;

@Service
@Transactional
public class AuthService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private VerificationTokenRepo verificationTokenRepo;  
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	 AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user=new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now()); //get current instant of the system clock
		user.setEnabled(false);
		userRepo.save(user);
		
		String token =generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
	}

	private String generateVerificationToken(User user) {
		
		String token=UUID.randomUUID().toString();
		VerificationToken verificationToken=new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepo.save(verificationToken);
		return token;
		
	}
	
	public void verifyAccount(String token) {
		Optional<VerificationToken> tkn=verificationTokenRepo.findByToken(token);
		tkn.orElseThrow(() -> new StringRedditException("Invalid Token"));
		fetchUserAndEnable(tkn.get());
	}
	
	@Transactional
	void fetchUserAndEnable(VerificationToken verificationToken) {
		String username=verificationToken.getUser().getUsername();
		User user=userRepo.findByUsername(username).orElseThrow(() -> new StringRedditException("user not found") );
	    user.setEnabled(true);
	    userRepo.save(user);
		
	}
	
	@Transactional
	public AuthenticationResponse login(LoginRequest loginRequest) {
	    Authentication authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token=jwtProvider.generateToken(authenticate);
		return new AuthenticationResponse(token,loginRequest.getUsername());
	}

}
