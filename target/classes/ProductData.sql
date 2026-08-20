INSERT INTO product
(product_name, product_price, product_image) VALUES
('114514', 1919810, '114514.jpg'),
('mouse',1200,'mouse.jpg'),
('headset',3000,'headset.jpg'),
('usb',500,'usb-c.jpg'),
('stand',1800,'stand.jpg');

UPDATE product
SET
    product_description = '這是一個特殊的測試商品',
    product_stock = 10,
    product_category = '測試商品'
WHERE product_id = 1;

UPDATE product
SET
    product_description = '高品質滑鼠，適合日常辦公與遊戲使用',
    product_stock = 20,
    product_category = '電腦周邊'
WHERE product_id = 2;

UPDATE product
SET
    product_description = '高音質耳機，適合音樂與遊戲使用',
    product_stock = 15,
    product_category = '電腦周邊'
WHERE product_id = 3;

UPDATE product
SET
    product_description = 'USB Type-C 傳輸線，支援高速資料傳輸',
    product_stock = 30,
    product_category = '線材'
WHERE product_id = 4;

UPDATE product
SET
    product_description = '實用型桌面支架，適合放置手機或平板',
    product_stock = 12,
    product_category = '桌面用品'
WHERE product_id = 5;
