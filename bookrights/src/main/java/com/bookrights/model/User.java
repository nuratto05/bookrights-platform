package com.bookrights.model;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(name="username", nullable=false)
	private String username;

	@Column(name="name", nullable=false)
	private String name;

	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="role", nullable=false)
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

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
	
	
	
	
}
