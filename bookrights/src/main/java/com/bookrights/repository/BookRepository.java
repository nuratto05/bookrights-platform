package com.bookrights.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bookrights.model.Book;
import com.bookrights.model.BookStatus;

public interface BookRepository extends JpaRepository<Book, Long>{

	Optional<Book> existsByIsbn(String isbn);

	Optional<Book> findByIsbn(String isbn);

	Page<Book> findAllByStatus(BookStatus available, Pageable pageable);

	
}
