CREATE TABLE books
(
    id IDENTITY PRIMARY KEY,
    title  VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    amount INT          NOT NULL DEFAULT 1,
    UNIQUE (title, author)
);


INSERT INTO books (title, author, amount)
VALUES ('To Kill a Mockingbird', 'Harper Lee', 1),
       ('1984', 'George Orwell', 1),
       ('Pride and Prejudice', 'Jane Austen', 1),
       ('The Great Gatsby', 'F. Scott Fitzgerald', 1),
       ('Moby Dick', 'Herman Melville', 1),
       ('War and Peace', 'Leo Tolstoy', 1),
       ('The Catcher in the Rye', 'J.D. Salinger', 1),
       ('Crime and Punishment', 'Fyodor Dostoevsky', 1),
       ('Brave New World', 'Aldous Huxley', 1),
       ('The Lord of the Rings', 'J.R.R. Tolkien', 1);

CREATE TABLE borrowed_books
(
    id IDENTITY PRIMARY KEY,
    member_id     BIGINT NOT NULL,
    book_id       BIGINT NOT NULL,
    borrowed_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES members (id) ON DELETE RESTRICT,
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,

    UNIQUE (member_id, book_id)
);


CREATE TABLE members
(
    id IDENTITY PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    membership_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
