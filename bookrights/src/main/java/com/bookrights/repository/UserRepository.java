package com.bookrights.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookrights.model.Book;
import com.bookrights.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String username);
	
	
	

}
