package com.bookrights.dto;

import com.bookrights.model.BookStatus;

import jakarta.validation.constraints.NotBlank;

public class CreateBookRequest {
	
	@NotBlank(message = "Book name cannot be empty")
	private String bookName;
	@NotBlank(message = "Author cannot be empty")
	private String author;
	@NotBlank(message = "ISBN cannot be empty")
	private String isbn;
	@NotBlank(message = "Category cannot be empty")
	private String category;
	@NotBlank(message = "Price cannot be empty")
	private double price;
	@NotBlank(message = "IMG cannot be empty")
	private String img;
	
	public CreateBookRequest(String bookName, String author, String isbn, String category, double price, String img) {
		super();
		this.bookName = bookName;
		this.author = author;
		this.isbn = isbn;
		this.category = category;
		this.price = price;
		this.img = img;
	}

	public String getBookName() {
		return bookName;
	}

	public String getAuthor() {
		return author;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getCategory() {
		return category;
	}

	public double getPrice() {
		return price;
	}

	public String getImg() {
		return img;
	}
	
	
}
