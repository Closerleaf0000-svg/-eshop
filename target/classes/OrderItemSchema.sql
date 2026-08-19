CREATE TABLE order_item (

    order_item_id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    product_image VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    product_price NUMERIC(10,2) NOT NULL,

    FOREIGN KEY(order_id)
        REFERENCES orders(order_id),

    FOREIGN KEY(product_id)
        REFERENCES product(product_id)

);