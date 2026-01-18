package com.bookrights.util;

import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookrights.dto.CreateBookRequest;
import com.bookrights.dto.UpdateBookRequest;
import com.bookrights.dto.BookResponse;
import com.bookrights.model.Book;
import com.bookrights.model.BookStatus;
import com.bookrights.repository.BookRepository;

@Service
public class BookService {

	private BookRepository bookRepo;
	
	public BookService(BookRepository bookRepository) {
		this.bookRepo = bookRepository;
	}

    public BookResponse updateBook(String isbn, UpdateBookRequest ubr) {
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

        bookRepo.save(book);

        BookResponse bookResponse = new BookResponse(book);
        return bookResponse;
    }
    
    public BookResponse createBook(CreateBookRequest cbr) {

        Book newBook = new Book(
				cbr.getAuthor(),
		        cbr.getBookName(),
		        cbr.getIsbn(),
		        cbr.getPrice(),
		        BookStatus.AVAILABLE,
		        cbr.getCategory(),
		        cbr.getImg()
				);
        
        bookRepo.save(newBook);

        BookResponse bookResponse = new BookResponse(newBook);
        return bookResponse;
    }
    
    public Page<BookResponse> getBooks(Pageable pageable) {
        return bookRepo.findAllByStatus("AVAILABLE", pageable)
                .map(BookResponse::new);
    }


	
}
