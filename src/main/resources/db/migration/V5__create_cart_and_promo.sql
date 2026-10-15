CREATE TABLE promo_codes
(
    id                  BIGSERIAL PRIMARY KEY,
    code                VARCHAR(64)    NOT NULL UNIQUE,
    discount_percentage NUMERIC(5, 2)  NOT NULL CHECK (discount_percentage >= 0 AND discount_percentage <= 100),
    is_active           BOOLEAN        NOT NULL DEFAULT TRUE
);

CREATE TABLE carts
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT    NOT NULL UNIQUE REFERENCES users (id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE cart_items
(
    id         BIGSERIAL PRIMARY KEY,
    cart_id    BIGINT  NOT NULL REFERENCES carts (id) ON DELETE CASCADE,
    product_id BIGINT  NOT NULL REFERENCES products (id),
    quantity   INTEGER NOT NULL CHECK (quantity > 0),
    UNIQUE (cart_id, product_id)
);
