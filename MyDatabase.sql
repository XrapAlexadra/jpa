CREATE TABLE role (
    role_id INT (32) UNIQUE PRIMARY KEY,
    role_name VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE users (
    user_id INT(32) AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    pass VARCHAR(255) NOT NULL,
    role INT(32) NOT NULL,
    CONSTRAINT FOREIGN KEY (role) REFERENCES role(role_id)
);

CREATE TABLE products (
    product_id INT(32) AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    price INT (32) NOT NULL,
    quantity INT(32)
);

CREATE TABLE status (
    status_id INT(32) AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE basket (
    id INT(32) AUTO_INCREMENT PRIMARY KEY,
    product_id INT(32) NOT NULL,
    quantity  INT(32) NOT NULL,
    user_id INT(32) NOT NULL,
    status_id INT(32) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (status_id) REFERENCES status(status_id)
);

CREATE TABLE city(
    city_id INT(32) AUTO_INCREMENT PRIMARY KEY,
    city_name VARCHAR(20) NOT NULL
);

CREATE TABLE shop_address (
    id_shop INT(32) AUTO_INCREMENT PRIMARY KEY,
    city_id INT(32) NOT NULL,
    street VARCHAR(30) NOT NULL,
    house_number INT(32) NOT NULL,
    FOREIGN KEY (city_id) REFERENCES city(city_id)
);

CREATE TABLE rating_product (
    rating_id  INT(32) AUTO_INCREMENT PRIMARY KEY,
    rating INT(32) NOT NULL,
    user_id INT(32) NOT NULL,
    product_id INT(32) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);