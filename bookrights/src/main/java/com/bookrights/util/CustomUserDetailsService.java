package com.bookrights.util;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookrights.model.CustomUserDetails;
import com.bookrights.model.User;
import com.bookrights.repository.UserRepository;

@Service
public class CustomUserDetailsService  implements UserDetailsService{
	
	private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepo = userRepository;
    }
    
    @Override
    public CustomUserDetails loadUserByUsername(String username){
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(user);
    }

}
