INSERT INTO products (id, title, description, price, rating, images, stock_quantity, created_at)
VALUES (
        1,
        'Корм',
        'Сухой корм',
        100.00,
        4.50,
        '["https://img/1.png","https://img/2.png","https://img/3.png"]' FORMAT JSON,
        5,
        CURRENT_TIMESTAMP
);

INSERT INTO promo_codes (code, discount_percentage, is_active)
VALUES ('SAVE10', 10.00, TRUE);
