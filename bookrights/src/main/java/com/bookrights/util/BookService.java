package com.bookrights.util;

import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bookrights.dto.CreateBookRequest;
import com.bookrights.dto.UpdateBookRequest;
import com.bookrights.dto.BookResponse;
import com.bookrights.model.Book;
import com.bookrights.model.BookStatus;
import com.bookrights.model.CustomUserDetails;
import com.bookrights.model.User;
import com.bookrights.model.UserRole;
import com.bookrights.repository.BookRepository;
import com.bookrights.repository.UserRepository;

@Service
public class BookService {

	private BookRepository bookRepo;
	private UserRepository userRepo;
	
	public BookService(BookRepository bookRepository, UserRepository userRepository) {
		this.bookRepo = bookRepository;
		this.userRepo = userRepository;
	}

    public BookResponse updateBook(String isbn, UpdateBookRequest ubr, CustomUserDetails userDetails) {
    	
        Book book = bookRepo.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        User user = userRepo.findById(userDetails.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if(!book.getOwner().getUserId().equals(user.getUserId())) {
        	throw new RuntimeException("You are not allowed to update this book");
        }

        ubr.getBookName().ifPresent(book::setBookName);
        ubr.getPrice().ifPresent(book::setPrice);
        ubr.getCategory().ifPresent(book::setCategory);
        ubr.getImg().ifPresent(book::setImg);

        bookRepo.save(book);

        BookResponse bookResponse = new BookResponse(book);
        return bookResponse;
    }
    
    public BookResponse createBook(CreateBookRequest cbr, CustomUserDetails userDetails) {
    	
    	User user = userRepo.findById(userDetails.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    	
        Book newBook = new Book(
				cbr.getAuthor(),
		        cbr.getBookName(),
		        cbr.getIsbn(),
		        cbr.getPrice(),
		        cbr.getCategory(),
		        cbr.getImg(),
		        user
				);
        
        bookRepo.save(newBook);

        BookResponse bookResponse = new BookResponse(newBook);
        return bookResponse;
    }
    
    public Page<BookResponse> getBooks(Pageable pageable) {
        return bookRepo.findAllByStatus("AVAILABLE", pageable)
                .map(BookResponse::new);
    }
    
    public Book getBook(String isbn) {
    	Book book = bookRepo.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));

		return book;
    }
    
    


	
}
