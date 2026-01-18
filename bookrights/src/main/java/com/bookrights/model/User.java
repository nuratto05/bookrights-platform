package com.bookrights.model;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(name="username", nullable=false,unique = true)
	private String username;
	
	@Column(name="password", nullable=false)
	private String password;

	@Column(name="name", nullable=false)
	private String name;

	@Column(name="email", nullable=false, unique = true)
	private String email;
	
	@Column(name="role", nullable=false)
	private UserRole role;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	ArrayList<Book> book = new ArrayList<>();
	
	public User() {}

	public User(String username, String name, String email, String password) {
		this.userId = userId;
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role != null ? role : UserRole.USER;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
