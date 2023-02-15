 package com.hn004.reddit.Entity;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	@NotBlank(message="Username cannot be empty")
	private String username;
	@NotBlank(message ="Password is required" )
	private String password;
	@Email
	@NotEmpty(message = "email is required")
	private String email;
	private Instant created;
	private boolean enabled;  //user will be enabled after verification

}
