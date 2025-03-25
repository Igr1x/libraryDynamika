CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255),
    birth_date DATE NOT NULL
);

CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) NOT NULL
);

CREATE TABLE book_loans (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    borrowed_at DATE NOT NULL,
    returned_at DATE DEFAULT NULL,

    CONSTRAINT fk_book_loans_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_book_loans_book FOREIGN KEY (book_id) REFERENCES book(id)
);
