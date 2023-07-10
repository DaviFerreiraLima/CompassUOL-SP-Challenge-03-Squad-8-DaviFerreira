DROP DATABASE IF EXISTS challenge3;

CREATE DATABASE IF NOT EXISTS challenge3;
USE challenge3;

CREATE TABLE IF NOT EXISTS category (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user (
  id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
  id INT PRIMARY KEY AUTO_INCREMENT,
  date DATE,
  description VARCHAR(150) NOT NULL,
  name VARCHAR(255) NOT NULL,
  img_url VARCHAR(255),
  price DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS role (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS product_category (
  product_id INT,
  category_id INT,
  PRIMARY KEY (product_id, category_id),
  FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
  FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_role (
  user_id INT,
  role_id INT,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
  FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

INSERT INTO role (name) VALUES ('ROLE_OPERATOR');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');

INSERT INTO user (first_name, last_name, email, password)
VALUES ('Bob', 'Smith', 'bob@gmail.com', '$2a$10$Rt3N/pgtPy2D3A/nsCBYteKYKZfflqAaIwCy9FLksF2hcm1.1OFhG');

SET @operatorId = (SELECT id FROM role WHERE name = 'ROLE_OPERATOR');
SET @adminId = (SELECT id FROM role WHERE name = 'ROLE_ADMIN');

INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM user WHERE email = 'bob@gmail.com'), @operatorId);
INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id FROM user WHERE email = 'bob@gmail.com'), @adminId);


INSERT INTO category (name) VALUES ('Electronics');
INSERT INTO category (name) VALUES ('Clothing');
INSERT INTO category (name) VALUES ('Home');

INSERT INTO product (date, description, name, img_url, price)
VALUES ('2023-01-01', 'Smartphone with high-performance specifications', 'Smartphone X', 'https://example.com/smartphone.jpg', 999.99);
INSERT INTO product (date, description, name, img_url, price)
VALUES ('2023-02-01', 'Comfortable and stylish t-shirt', 'T-Shirt', 'https://example.com/tshirt.jpg', 29.99);
INSERT INTO product (date, description, name, img_url, price)
VALUES ('2023-03-01', 'Durable and practical kitchen utensils set', 'Kitchen Utensils', 'https://example.com/kitchenutensils.jpg', 49.99);


INSERT INTO product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO product_category (product_id, category_id) VALUES (1, 2);
INSERT INTO product_category (product_id, category_id) VALUES (2, 2);
INSERT INTO product_category (product_id, category_id) VALUES (3, 3);