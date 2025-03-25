CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255),
    birth_date DATE NOT NULL
);

CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) NOT NULL
);

CREATE TABLE book_loans (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    book_id INT NOT NULL,
    borrowed_at DATE NOT NULL,
    returned_at DATE DEFAULT NULL,

    CONSTRAINT fk_book_loans_client FOREIGN KEY (client_id) REFERENCES client(id),
    CONSTRAINT fk_book_loans_book FOREIGN KEY (book_id) REFERENCES book(id)
);
