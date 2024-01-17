CREATE TABLE IF NOT EXISTS product(
	product_id INT PRIMARY KEY NOT NULL,
	product_name VARCHAR(100) NOT NULL UNIQUE,
	product_price DECIMAL(10, 2)
);