package com.bookrights.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.bookrights.dto.BookResponse;
import com.bookrights.dto.CreateBookRequest;
import com.bookrights.dto.UpdateBookRequest;
import com.bookrights.model.Book;
import com.bookrights.model.CustomUserDetails;
import com.bookrights.model.User;
import com.bookrights.model.UserRole;
import com.bookrights.repository.BookRepository;
import com.bookrights.repository.UserRepository;
import com.bookrights.util.BookService;

@RestController
public class BookController {

	private final BookRepository bookRepo;
	private BookService bookService;
	private UserRepository userRepo;
	
	public BookController(BookRepository bookRepo, BookService bookService, UserRepository userRepository) {
		this.bookRepo = bookRepo;
		this.bookService = bookService;
		this.userRepo = userRepository;
	}

	// Create Book
	@PostMapping("/api/createBook")
	public ResponseEntity<?> createBook(@AuthenticationPrincipal CustomUserDetails user, @RequestBody CreateBookRequest cbr) {
		Optional<Book> bookOptional = bookRepo.findByIsbn(cbr.getIsbn());

		if (bookOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Book with ISBN exists");
		}
		
		System.out.println(cbr.toString());
		System.out.println(user.toString());
		
		BookResponse book = bookService.createBook(cbr, user);
		return ResponseEntity.ok(book);
	}

	// Update Book
	@PostMapping("/api/updateBook/{isbn}")
	public ResponseEntity<?> updateBook(@AuthenticationPrincipal CustomUserDetails user, @RequestBody UpdateBookRequest ubr, @PathVariable String isbn) {
		
		if (isbn == null || isbn.isBlank()) {
	        return ResponseEntity.badRequest()
	                             .body("ISBN cannot be null or empty");
	    }
		
		BookResponse updatedBook = bookService.updateBook(isbn, ubr, user);
		
		return ResponseEntity.ok(updatedBook);
	}

	// Delete Book
	@DeleteMapping("/api/deleteBook/{isbn}")
	public ResponseEntity<?> deleteBook(@AuthenticationPrincipal CustomUserDetails user, @PathVariable String isbn) {		
		
		if (isbn == null || isbn.isBlank()) {
	        return ResponseEntity.badRequest()
	                             .body("ISBN cannot be null or empty");
	    }
		
		return bookService.deleteBook(user, isbn);
	}
	
	// Get all available books
	@GetMapping("/api/books")
	public ResponseEntity<Page<BookResponse>> getBooks(@AuthenticationPrincipal CustomUserDetails user, Pageable pageable) {
	    Page<BookResponse> books = bookService.getBooks(pageable);
	    
	    return ResponseEntity.ok(books);
	}
	
	//Get book detail
	@GetMapping("/api/book/{isbn}")
	public ResponseEntity<?> getBook(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable String isbn){
		
		if (isbn == null || isbn.isBlank()) {
	        return ResponseEntity.badRequest()
	                             .body("ISBN cannot be null or empty");
	    }
		
		User user = userRepo.findById(userDetails.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
		
		Book book = bookService.getBook(isbn);
		BookResponse brBook = new BookResponse(book);
		
		return ResponseEntity.ok(brBook);
		
	}
	
}
