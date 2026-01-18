package com.bookrights.dto;

public class CreateBookRequest {
	
	private String bookName;
	private String author;
	private String isbn;
	private String category;
	private String status;
	private double price;
	private String img;
	
	public CreateBookRequest(String bookName, String author, String isbn, String category, String status, double price, String img) {
		super();
		this.bookName = bookName;
		this.author = author;
		this.isbn = isbn;
		this.category = category;
		this.price = price;
		this.img = img;
		this.status = status;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
