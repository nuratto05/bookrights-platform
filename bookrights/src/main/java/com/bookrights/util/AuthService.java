package com.bookrights.util;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookrights.dto.LoginRequest;
import com.bookrights.dto.RegisterRequest;
import com.bookrights.model.User;
import com.bookrights.repository.UserRepository;
import com.bookrights.security.JwtUtil;

@Service
public class AuthService {

	 private final UserRepository userRepo;
//	    private final PasswordEncoder passwordEncoder;
	 //		public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtService) {
//			this.userRepo = userRepo;
//			this.passwordEncoder = passwordEncoder;
//			this.jwtService = jwtService;
//		}
	 
	    private final JwtUtil jwtService;
	    
	    public AuthService(UserRepository userRepo, JwtUtil jwtService) {
			this.userRepo = userRepo;
			this.jwtService = jwtService;
		}
	    
	    public ResponseEntity<?> login(LoginRequest request) {

	        // 1️⃣ Validate request
	        if (request == null) {
	            return ResponseEntity.badRequest().body("Request body is missing");
	        }

	        if (request.getUsername() == null || request.getUsername().isBlank()) {
	            return ResponseEntity.badRequest().body("Username is required");
	        }

	        if (request.getPassword() == null || request.getPassword().isBlank()) {
	            return ResponseEntity.badRequest().body("Password is required");
	        }

	        try {
	            User user = userRepo.findByUsername(request.getUsername())
	                    .orElseThrow(() -> new RuntimeException("Invalid username or password"));

//	            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//	                        .body("Invalid username or password");
//	            }
	            if (!request.getPassword().equals(user.getPassword())) {
		            throw new RuntimeException("Invalid password");
		        }

	            String token = jwtService.generateToken(user);

	            return ResponseEntity.ok().body(token);

	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	        }
	    }

	    
	    public ResponseEntity<?> register(RegisterRequest request) {
	    	
	    	if (request == null) {
	    	    return ResponseEntity.badRequest().body("Request body is missing");
	    	}

	    	if (request.getUsername() == null || request.getUsername().isBlank()) {
	    	    return ResponseEntity.badRequest().body("Username is required");
	    	}

	    	if (request.getEmail() == null || request.getEmail().isBlank()) {
	    	    return ResponseEntity.badRequest().body("Email is required");
	    	}

	    	if (request.getPassword() == null || request.getPassword().isBlank()) {
	    	    return ResponseEntity.badRequest().body("Password is required");
	    	}

	    	if (request.getName() == null || request.getName().isBlank()) {
	    	    return ResponseEntity.badRequest().body("Name is required");
	    	}

		    try {
		    	// Check email
		    	if (userRepo.findByEmail(request.getEmail()).isPresent()) {
		    	    return ResponseEntity.status(HttpStatus.CONFLICT)
		    	        .body("User with email already exists");
		    	}

		    	// Check username
		    	if (userRepo.findByUsername(request.getUsername()).isPresent()) {
		    	    return ResponseEntity.status(HttpStatus.CONFLICT)
		    	        .body("Username already exists");
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
		        
		    } catch(Exception e) {
		    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		    }
	    }
	
}
