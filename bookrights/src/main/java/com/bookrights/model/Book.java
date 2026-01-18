package com.bookrights.model;

import jakarta.persistence.*;

@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="author", nullable=false)
	private String author;
	
	@Column(name="book_name", nullable=false)
	private String bookName;
	
	@Column(name="isbn", nullable=false)
	private String isbn;
	
	@Column(name="price")
	private double price;
	
	@Column(name="status")
	private String status;
	
	@Column(name="category", nullable=false)
	private String category;
	
	@Column(name="img")
	private String img;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	private User owner;
	
	public Book() {}
	
	public Book( String author, String bookName, String isbn, double price, String status, String category, String img) {
		this.author = author;
		this.bookName = bookName;
		this.isbn = isbn;
		this.price = price;
		this.status = status;
		this.category = category;
		this.img = img;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	
}
