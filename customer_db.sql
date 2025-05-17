CREATE DATABASE customer_db;

-- Master Data Tables
CREATE TABLE country (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE city (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    country_code VARCHAR(10) NOT NULL,
    FOREIGN KEY (country_code) REFERENCES country(code)
);

-- Main Customer Table
CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    dob DATE NOT NULL,
    nic VARCHAR(20) NOT NULL UNIQUE
);

-- Customer Mobile Numbers (1-to-Many)
CREATE TABLE mobile_number (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    number VARCHAR(20) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

-- Customer Addresses (1-to-Many)
CREATE TABLE address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city_code VARCHAR(10) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (city_code) REFERENCES city(code)
);

-- Family Relationships (Many-to-Many)
CREATE TABLE customer_family (
    customer_id BIGINT NOT NULL,
    family_member_id BIGINT NOT NULL,
    PRIMARY KEY (customer_id, family_member_id),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (family_member_id) REFERENCES customer(id),
    CHECK (customer_id <> family_member_id)
);

-- Indexes for Performance
CREATE INDEX idx_customer_nic ON customer(nic);
CREATE INDEX idx_mobile_customer ON mobile_number(customer_id);
CREATE INDEX idx_address_customer ON address(customer_id);

