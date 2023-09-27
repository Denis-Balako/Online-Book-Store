INSERT INTO cart
    (id, user_id)
VALUES
    (2, 2),
    (3, 3);

INSERT INTO cart_item
    (id, cart_id, book_id, quantity)
VALUES
    (2, 2, 1, 1),
    (3, 2, 2, 1),
    (4, 3, 1, 1);
