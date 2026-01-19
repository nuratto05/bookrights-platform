package com.bookrights.dto;

import com.bookrights.model.Book;

import jakarta.persistence.Column;

public class TransactionRequest {
	
	private String bookName;
	private String bookIsbn;
	private double price;
	
	public TransactionRequest(String bookName, String bookIsbn, double price) {
		this.bookName = bookName;
		this.bookIsbn = bookIsbn;
		this.price = price;
	}

	public String getBookName() {
		return bookName;
	}

	public String getBookIsbn() {
		return bookIsbn;
	}

	public double getPrice() {
		return price;
	}

}
