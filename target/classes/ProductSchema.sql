CREATE TABLE product (
    
    product_id BIGSERIAL PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    product_price NUMERIC(10,2) NOT NULL,
    product_image VARCHAR(255) NOT NULL
);

ALTER TABLE product
ADD COLUMN product_description TEXT,
ADD COLUMN product_stock INTEGER,
ADD COLUMN product_category VARCHAR(100);