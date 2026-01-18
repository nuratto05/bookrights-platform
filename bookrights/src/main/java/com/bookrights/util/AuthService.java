package com.bookrights.util;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookrights.dto.LoginRequest;
import com.bookrights.dto.RegisterRequest;
import com.bookrights.model.User;
import com.bookrights.repository.UserRepository;
import com.bookrights.security.JwtUtil;

@Service
public class AuthService {

	 private final UserRepository userRepo;
	    private final PasswordEncoder passwordEncoder;
	    private final JwtUtil jwtService;

	    public AuthService(UserRepository userRepo,
	                       PasswordEncoder passwordEncoder,
	                       JwtUtil jwtService) {
	        this.userRepo = userRepo;
	        this.passwordEncoder = passwordEncoder;
	        this.jwtService = jwtService;
	    }
	    
	    public String login(LoginRequest request) {
	    
	    	User user = userRepo.findByUsername(request.getUsername())
	                .orElseThrow(() -> new RuntimeException("Invalid username"));

	        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	            throw new RuntimeException("Invalid password");
	        }

	        return jwtService.generateToken(user);
	    }
	    
	    public ResponseEntity<?> register(RegisterRequest request) {
		    
	    	Optional<User> userOptional = userRepo.findByEmail(request.getEmail());
	    	
	    	if(userOptional.isPresent()) {
	    		return ResponseEntity.status(HttpStatus.CONFLICT).body("User with email already exists");
	    	}
	    	
	    	User user = new User(
	    			request.getUsername(),
	    			request.getName(),
	    			request.getEmail(),
	    			request.getPassword()
	    			);
	    	
	    	userRepo.save(user);

	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body("User registered successfully");
	    }
	
}
