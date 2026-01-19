package com.bookrights.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	
	private String username;
	private String password;
	
	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
