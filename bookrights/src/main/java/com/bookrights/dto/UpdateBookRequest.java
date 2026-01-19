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

	public Optional<Double> getPrice() {
		return price;
	}

	public Optional<String> getCategory() {
		return category;
	}

	public Optional<String> getImg() {
		return img;
	}
	
	
}
