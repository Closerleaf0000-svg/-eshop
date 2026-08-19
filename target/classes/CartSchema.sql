CREATE TABLE cart (
    cart_id BIGSERIAL PRIMARY KEY,
    member_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,

    FOREIGN KEY(member_id)
        REFERENCES member(member_id),
    FOREIGN KEY(product_id)
        REFERENCES product(product_id)
);