CREATE TABLE users (
   user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
   username VARCHAR(255),
   password VARCHAR(255),
   first_name VARCHAR(255),
   last_name VARCHAR(255),
   email VARCHAR(255)
);