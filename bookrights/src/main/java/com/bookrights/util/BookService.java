package com.bookrights.util;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookrights.dto.CreateBookRequest;
import com.bookrights.dto.UpdateBookRequest;
import com.bookrights.model.Book;
import com.bookrights.repository.BookRepository;

@Service
public class BookService {

	private BookRepository bookRepo;
	
	public BookService(BookRepository bookRepository) {
		this.bookRepo = bookRepository;
	}

    public Book updateBook(String isbn, UpdateBookRequest ubr) {
        Book book = bookRepo.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        ubr.getPrice().ifPresent(price -> {
            if (price <= 0) {
                throw new IllegalArgumentException("Price must be greater than 0");
            }
        });

        ubr.getBookName().ifPresent(book::setBookName);
        ubr.getPrice().ifPresent(book::setPrice);
        ubr.getCategory().ifPresent(book::setCategory);
        ubr.getImg().ifPresent(book::setImg);

        return bookRepo.save(book);
    }
    
    public Book createBook(CreateBookRequest cbr) {
    	
		if (cbr.getAuthor() == null || cbr.getAuthor().isEmpty()) {
			throw new IllegalArgumentException("Author is required");
		}
		if (cbr.getBookName() == null || cbr.getBookName().isEmpty()) {
			throw new IllegalArgumentException("Book name is required");
		}
		if (cbr.getIsbn() == null || cbr.getIsbn().isEmpty()) {
			throw new IllegalArgumentException("ISBN is required");
		}
		if (cbr.getPrice() <= 0) {
			throw new IllegalArgumentException("Price is required and must be greater than 0");
		}
		if (cbr.getCategory() == null || cbr.getCategory().isEmpty()) {
			throw new IllegalArgumentException("Category is required");
		}

        Book newBook = new Book(
				cbr.getAuthor(),
		        cbr.getBookName(),
		        cbr.getIsbn(),
		        cbr.getPrice(),
		        "AVAILABLE",
		        cbr.getCategory(),
		        cbr.getImg()
				);

        return bookRepo.save(newBook);
    }
	
}
