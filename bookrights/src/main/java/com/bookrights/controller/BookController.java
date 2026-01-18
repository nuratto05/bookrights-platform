package com.bookrights.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.bookrights.dto.CreateBookRequest;
import com.bookrights.dto.UpdateBookRequest;
import com.bookrights.model.Book;
import com.bookrights.model.User;
import com.bookrights.repository.BookRepository;
import com.bookrights.util.BookService;

@RestController
public class BookController {

	private final BookRepository bookRepo;
	private BookService bookService;
	
	public BookController(BookRepository bookRepo, BookService bookService) {
		this.bookRepo = bookRepo;
		this.bookService = bookService;
	}

	// Create Book
	@PostMapping("/api/createBook")
	public ResponseEntity<?> createBook(@RequestBody CreateBookRequest cbr) {
		Optional<Book> bookOptional = bookRepo.findByIsbn(cbr.getIsbn());

		if (bookOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Book with ISBN exists");
		}
		
		Book book = bookService.createBook(cbr);
		return ResponseEntity.ok(book);
	}

	// Update Book
	@PostMapping("/api/updateBook/{isbn}")
	public ResponseEntity<?> updateBook(@RequestBody UpdateBookRequest ubr, @PathVariable String isbn) {
		
		if (isbn == null || isbn.isBlank()) {
	        return ResponseEntity.badRequest()
	                             .body("ISBN cannot be null or empty");
	    }
		
		Optional<Book> bookOptional = bookRepo.existsByIsbn(isbn);

		if (!bookOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Book with ISBN does not exist");
		}
		
		Book updatedBook = bookService.updateBook(isbn, ubr);
		
		return ResponseEntity.ok(updatedBook);
	}

	// Delete Book
	@DeleteMapping("/api/deleteBook/{isbn}")
	public ResponseEntity<?> deleteBook( @PathVariable String isbn) {
		
		if (isbn == null || isbn.isBlank()) {
	        return ResponseEntity.badRequest()
	                             .body("ISBN cannot be null or empty");
	    }

		Optional<Book> bookOptional = bookRepo.findByIsbn(isbn);

		if (bookOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with ISBN does not exist or invalid data");
		}

		Book book = bookOptional.get();
		bookRepo.delete(book);
		
		return ResponseEntity.ok("Book Removed");
	}
	
	// Get all available books
	@GetMapping("/api/getBooks")
	public ResponseEntity<List<Book>> getBooks() {
		List<Book> books = bookRepo.findAllByStatus("AVAILABLE");
		return ResponseEntity.ok(books);
	}
	
	//Get book detail
	@GetMapping("/api/getBook/{isbn}")
	public ResponseEntity<?> getBook(@PathVariable String isbn){
		
		if (isbn == null || isbn.isBlank()) {
	        return ResponseEntity.badRequest()
	                             .body("ISBN cannot be null or empty");
	    }
		
		Optional<Book> bookOptional = bookRepo.findByIsbn(isbn);
		if (bookOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with ISBN does not exist or invalid data");
		}

		Book book = bookOptional.get();
		
		return ResponseEntity.ok(book);
		
	}
	
}
