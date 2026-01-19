package com.bookrights.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transaction_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	private User buyer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id", nullable = false)
	private User seller;
	
	@Column(name="status", nullable=false)
	private TransactionStatus status;
	
	@Column(name="book_name", nullable=false)
	private String bookName;
	
	@Column(name="book_isbn", nullable=false)
	private String bookIsbn;
	
	@Column(name="price", nullable=false)
	private double price;
	
	public Transaction( User buyer, User seller, String bookName, String bookIsbn, double price) {
		this.buyer = buyer;
		this.seller = seller;
		this.status = TransactionStatus.PENDING;
		this.bookName = bookName;
		this.bookIsbn = bookIsbn;
		this.price = price;
	}

	public Long getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(Long transaction_id) {
		this.transaction_id = transaction_id;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookIsbn() {
		return bookIsbn;
	}

	public void setBookIsbn(String bookIsbn) {
		this.bookIsbn = bookIsbn;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}
	
	
	
}
