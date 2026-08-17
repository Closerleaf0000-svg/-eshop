CREATE TABLE cart (

    cart_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,

    FOREIGN KEY(product_id)
        REFERENCES product(product_id)
);