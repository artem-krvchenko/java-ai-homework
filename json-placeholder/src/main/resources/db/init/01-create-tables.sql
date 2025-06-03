-- Create sequences
CREATE SEQUENCE IF NOT EXISTS seq_auth_user_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS seq_geo_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS seq_address_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS seq_company_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS seq_app_user_id START WITH 1 INCREMENT BY 1;

-- Create auth_user table
CREATE TABLE IF NOT EXISTS auth_user (
    id BIGINT PRIMARY KEY DEFAULT nextval('seq_auth_user_id'),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

-- Create Geo table
CREATE TABLE IF NOT EXISTS geo (
    id BIGINT PRIMARY KEY DEFAULT nextval('seq_geo_id'),
    lat VARCHAR(255) NOT NULL,
    lng VARCHAR(255) NOT NULL
);

-- Create Address table
CREATE TABLE IF NOT EXISTS address (
    id BIGINT PRIMARY KEY DEFAULT nextval('seq_address_id'),
    street VARCHAR(255) NOT NULL,
    suite VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    zipcode VARCHAR(255) NOT NULL,
    geo_id BIGINT NOT NULL,
    FOREIGN KEY (geo_id) REFERENCES geo(id)
);

-- Create Company table
CREATE TABLE IF NOT EXISTS company (
    id BIGINT PRIMARY KEY DEFAULT nextval('seq_company_id'),
    name VARCHAR(255) NOT NULL,
    catch_phrase VARCHAR(255) NOT NULL,
    bs VARCHAR(255) NOT NULL
);

-- Create App User table
CREATE TABLE IF NOT EXISTS app_user (
    id BIGINT PRIMARY KEY DEFAULT nextval('seq_app_user_id'),
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    address_id BIGINT NOT NULL,
    phone VARCHAR(255) NOT NULL,
    website VARCHAR(255) NOT NULL,
    company_id BIGINT NOT NULL,
    FOREIGN KEY (address_id) REFERENCES address(id),
    FOREIGN KEY (company_id) REFERENCES company(id)
);

-- Create indexes for App User table
CREATE INDEX IF NOT EXISTS idx_app_user_username ON app_user(username);
CREATE INDEX IF NOT EXISTS idx_app_user_email ON app_user(email);
CREATE INDEX IF NOT EXISTS idx_auth_user_email ON auth_user(email);

-- Create composite index for name and username for faster searches
CREATE INDEX IF NOT EXISTS idx_app_user_name_username ON app_user (name, username);

-- Add unique constraints
ALTER TABLE app_user ADD CONSTRAINT uk_app_user_username UNIQUE (username);
ALTER TABLE app_user ADD CONSTRAINT uk_app_user_email UNIQUE (email); 