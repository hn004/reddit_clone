package com.hn004.reddit.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hn004.reddit.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);

}
