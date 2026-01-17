package com.bookrights.model;

import java.util.ArrayList;

public class User {
	
	private Long userId;
	private String username;
	private String name;
	private String email;
	private UserRole role;
	ArrayList<Book> book = new ArrayList<>();
	
	public User() {}

	public User(Long userId, String username, String name, String email, UserRole role) {
		super();
		this.userId = userId;
		this.username = username;
		this.name = name;
		this.email = email;
		this.role = role != null ? role : UserRole.BUYER;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
