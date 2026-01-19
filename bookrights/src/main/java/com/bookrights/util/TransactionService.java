package com.bookrights.util;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookrights.dto.TransactionRequest;
import com.bookrights.model.Book;
import com.bookrights.model.CustomUserDetails;
import com.bookrights.model.Transaction;
import com.bookrights.model.TransactionStatus;
import com.bookrights.model.User;
import com.bookrights.repository.TransactionRepository;
import com.bookrights.repository.UserRepository;
import com.bookrights.repository.BookRepository;

@Service
public class TransactionService {
	
	private UserRepository userRepo;
	private BookRepository bookRepo;
	private TransactionRepository transactionRepo;
	
	
	public TransactionService(UserRepository userRepository, TransactionRepository transactionRepository, BookRepository bookRepository) {
		this.userRepo = userRepository;
		this.bookRepo = bookRepository;
		this.transactionRepo = transactionRepository;
	}


	public ResponseEntity<?> startTransaction(CustomUserDetails user, TransactionRequest request) {
		
		if (request == null) {
			throw new IllegalArgumentException("Request body is missing");
		}

		if (request.getBookName() == null || request.getBookName().isBlank()) {
			throw new IllegalArgumentException("Book Name is required");
		}

		if (request.getPrice() < 0) {
			throw new IllegalArgumentException("Price must not be below 0");
		}
		
	    if (request.getBookIsbn() == null || request.getBookIsbn().isBlank()) {
	        throw new IllegalArgumentException("ISBN is required");
	    }

	    User buyer = userRepo.findById(user.getUserId())
	            .orElseThrow(() -> new RuntimeException("Buyer does not exist"));

	    Book book = bookRepo.findByIsbn(request.getBookIsbn())
	            .orElseThrow(() ->
	                    new RuntimeException("Book with ISBN " + request.getBookIsbn() + " does not exist"));

	    // Prevent duplicate transactions for same buyer + book
	    Optional<Transaction> existing =
	            transactionRepo.findByBuyerUserIdAndBookIsbn(buyer.getUserId(), book.getIsbn());

	    if (existing.isPresent()) {
	        throw new RuntimeException("Transaction already exists for this book and buyer");
	    }

	    Transaction newTransaction = new Transaction(
	            buyer,
	            book.getOwner(),
	            book.getBookName(),
	            book.getIsbn(),
	            book.getPrice()
	    );

	    transactionRepo.save(newTransaction);

	    return ResponseEntity.ok(newTransaction);
	}


}
