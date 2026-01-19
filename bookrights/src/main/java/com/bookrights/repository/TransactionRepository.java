package com.bookrights.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookrights.model.Book;
import com.bookrights.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	Optional<Transaction> findByBuyerUserIdAndBookIsbn(Long buyerId, String bookIsbn);

}
