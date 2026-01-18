package com.bookrights.controller;

import org.springframework.http.ResponseEntity;
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
	
	public UserController(AuthService authService) {
		this.authService = authService;
	}




	@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletResponse response) {

        String token = authService.login(request);
        
        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);       // 🔒 JS cannot access
        cookie.setSecure(true);         // 🔒 HTTPS only
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 10); // 10 hours

        response.addCookie(cookie);

        return ResponseEntity.ok().body("Login Succesful");
    }
	
	@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        return authService.register(request);
    }
	
	

}
