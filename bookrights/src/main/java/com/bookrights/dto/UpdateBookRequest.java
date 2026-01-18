package com.bookrights.dto;

import java.util.Optional;

public class UpdateBookRequest {
	
    private Optional<String> bookName = Optional.empty();
    private Optional<Double> price = Optional.empty();
    private Optional<String> category = Optional.empty();
    private Optional<String> img = Optional.empty();
    
	public UpdateBookRequest(Optional<String> bookName, Optional<Double> price, Optional<String> category,
			Optional<String> img) {
		super();
		this.bookName = bookName;
		this.price = price;
		this.category = category;
		this.img = img;
	}

	public Optional<String> getBookName() {
		return bookName;
	}

	public void setBookName(Optional<String> bookName) {
		this.bookName = bookName;
	}

	public Optional<Double> getPrice() {
		return price;
	}

	public void setPrice(Optional<Double> price) {
		this.price = price;
	}

	public Optional<String> getCategory() {
		return category;
	}

	public void setCategory(Optional<String> category) {
		this.category = category;
	}

	public Optional<String> getImg() {
		return img;
	}

	public void setImg(Optional<String> img) {
		this.img = img;
	}	
	
}
