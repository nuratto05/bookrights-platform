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
	public ResponseEntity<?> deleteBook( @PathVariable String isbn) {
		
		User user = new User();
    	user.setUserId((long)1);
    	user.setUsername("testuser");
    	user.setPassword("password123"); // will be encoded by your AuthService
    	user.setName("Test User");
    	user.setEmail("testuser@example.com");
    	user.setRole(UserRole.USER);
		
		
		
		if (isbn == null || isbn.isBlank()) {
	        return ResponseEntity.badRequest()
	                             .body("ISBN cannot be null or empty");
	    }
		
		Book book = bookService.getBook(isbn);
		
		if(!book.getOwner().getUserId().equals(user.getUserId())) {
        	throw new RuntimeException("You are not allowed to update this book");
        }
		
		bookRepo.delete(book);
		
		return ResponseEntity.ok("Book Removed");
	}
	
	// Get all available books
	@GetMapping("/api/books")
	public ResponseEntity<Page<BookResponse>> getBooks(Pageable pageable) {
	    Page<BookResponse> books = bookService.getBooks(pageable);
	    
	    return ResponseEntity.ok(books);
	}
	
	//Get book detail
	@GetMapping("/api/book/{isbn}")
	public ResponseEntity<?> getBook(@PathVariable String isbn){
		
		if (isbn == null || isbn.isBlank()) {
	        return ResponseEntity.badRequest()
	                             .body("ISBN cannot be null or empty");
	    }
		
		Book book = bookService.getBook(isbn);
		BookResponse brBook = new BookResponse(book);
		
		return ResponseEntity.ok(brBook);
		
	}
	
}
