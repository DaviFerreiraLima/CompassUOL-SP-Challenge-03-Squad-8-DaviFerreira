

create database challenge3
use challenge3

CREATE TABLE category (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE user (
  id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  roles VARCHAR(20) NOT NULL
);

CREATE TABLE product (
  id INT PRIMARY KEY AUTO_INCREMENT,
  date DATE,
  description  VARCHAR(150) NOT NULL,
  name VARCHAR(255) NOT NULL,
  img_url VARCHAR(255),
  price DECIMAL(10, 2),
  category_id INT,
  FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE role (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL
);

CREATE TABLE product_category (
  product_id INT,
  category_id INT,
  PRIMARY KEY (product_id, category_id),
  FOREIGN KEY (product_id) REFERENCES product(id),
  FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE user_role (
  user_id INT,
  role_id INT,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (role_id) REFERENCES role(id)
);