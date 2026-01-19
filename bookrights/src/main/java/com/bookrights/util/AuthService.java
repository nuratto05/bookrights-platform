package com.bookrights.util;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
	private final JwtUtil jwtService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UserRepository userRepo, JwtUtil jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		
	}

	public String login(LoginRequest request) {

		// 1️⃣ Validate request
		if (request == null) {
			throw new IllegalArgumentException("Request body is missing");
		}

		if (request.getUsername() == null || request.getUsername().isBlank()) {
			throw new IllegalArgumentException("Username is required");
		}

		if (request.getPassword() == null || request.getPassword().isBlank()) {
			throw new IllegalArgumentException("Password is required");
		}

		try {

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			User user = userRepo.findByUsername(request.getUsername())
					.orElseThrow(() -> new RuntimeException("Invalid username or password"));

			String token = jwtService.generateToken(user);

			return token;

		} catch (BadCredentialsException e) {
			throw e;
		} catch(Exception e) {
			throw new RuntimeException("Login failed: " + e.getMessage(), e);
		}
	}

	public void register(RegisterRequest request) {

		if (request == null) {
			throw new IllegalArgumentException("Request body is missing");
		}

		if (request.getUsername() == null || request.getUsername().isBlank()) {
			throw new IllegalArgumentException("Username is required");
		}

		if (request.getEmail() == null || request.getEmail().isBlank()) {
			throw new IllegalArgumentException("Email is required");
		}

		if (request.getPassword() == null || request.getPassword().isBlank()) {
			throw new IllegalArgumentException("Password is required");
		}

		if (request.getName() == null || request.getName().isBlank()) {
			throw new IllegalArgumentException("Name is required");
		}

		// Check email
		if (userRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new IllegalArgumentException("User with email already exists");
		}

		// Check username
		if (userRepo.findByUsername(request.getUsername()).isPresent()) {
			throw new IllegalArgumentException("Username already exists");
		}

		String hashedPassword = passwordEncoder.encode(request.getPassword());

		User user = new User(request.getUsername(), request.getName(), request.getEmail(), hashedPassword);

		userRepo.save(user);
	}

}
