package com.bookrights.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.bookrights.dto.LoginRequest;
import com.bookrights.dto.RegisterRequest;
import com.bookrights.util.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UserController {

	private final AuthService authService;
	private final AuthenticationManager authenticationManager;

	public UserController(AuthService authService, AuthenticationManager authenticationManager) {
		super();
		this.authService = authService;
		this.authenticationManager = authenticationManager;
	}
	

	@GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletResponse response) {

        try {
            String token = authService.login(request);
            
            String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
            
            Cookie cookie = new Cookie("JWT", encodedToken);
            cookie.setHttpOnly(true);       // 🔒 JS cannot access
            cookie.setSecure(true);         // 🔒 HTTPS only
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 10); // 10 hours

            response.addCookie(cookie);
            System.out.println(token.toString());
            System.out.println(encodedToken.toString());
            System.out.println(cookie.toString());
            return ResponseEntity.ok().body("Login Succesful");
            
        } catch (IllegalArgumentException | BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
	
	@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

		try {
            authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }
	
	

}
