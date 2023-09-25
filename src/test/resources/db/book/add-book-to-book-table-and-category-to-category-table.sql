INSERT INTO book
    (id, title, author, isbn, price, description, cover_image, is_deleted)
VALUES
    (1, 'Title 1', 'Author 1', '978-0545010221', 10, 'Some description', 'Image 1', false),
    (2, 'Title 2', 'Author 2', '978-0735619678', 1, 'Some description', 'Image 2', false);

INSERT INTO category
    (id, name, description, is_deleted)
VALUES
    (1, 'Category 1', 'Category 1 description', false),
    (2, 'Category 2', 'Category 2 description', false);

INSERT INTO book_category
    (book_id, category_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2);
