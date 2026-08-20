CREATE TABLE orders (
    order_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    total_amount NUMERIC(10,2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL
);