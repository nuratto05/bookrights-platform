CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    author VARCHAR(255) NOT NULL,
    book_name VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    price DECIMAL(10,2),
    status VARCHAR(20) NOT NULL,
    category VARCHAR(100) NOT NULL,
    img VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id)
);