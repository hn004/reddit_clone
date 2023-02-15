package com.hn004.reddit.Service;

import static java.util.Collections.singletonList;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;
//import org.hibernate.internal.util.collections.SingletonIterator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hn004.reddit.Entity.User;
import com.hn004.reddit.Repository.UserRepo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

//	private UserRepo userRepo;
//	
//	@Override
//	@Transactional
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<User> userOptional=userRepo.findByUsername(username);
//		User user=userOptional.orElseThrow(()-> new 
//				StringRedditException("no user found with username:"+username));
//		return new org.springframework.security.core.userdetails.
//				User(user.getUsername(),
//				user.getPassword(), user.isEnabled(),
//				true,true,true,getAuthorities("USER"));
//	}
//
//
//	
//	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
//        return singletonList(new SimpleGrantedAuthority(role));
//    }
	
	private final UserRepo userRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found with username : " + username));

        return new org.springframework.security
                .core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }

}
