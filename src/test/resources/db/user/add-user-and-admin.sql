INSERT INTO users
    (id, email, password, first_name, last_name, shipping_address)
VALUES
    (2, 'user', '12345', 'user name', 'user surname', ''),
    (3, 'admin', '12345', 'admin name', 'admin surname', 'Nebraska');

INSERT INTO user_role
    (user_id, role_id)
VALUES
    (2, 2),
    (3, 1);
