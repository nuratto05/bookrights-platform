package com.bookrights.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookrights.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

	Optional<Book> existsByIsbn(String isbn);

	Optional<Book> findByIsbn(String isbn);

	List<Book> findAllByStatus(String string);

	
}
