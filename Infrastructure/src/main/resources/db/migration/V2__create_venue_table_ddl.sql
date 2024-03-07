CREATE TABLE venues (
   venue_id BIGINT PRIMARY KEY AUTO_INCREMENT,
   venue_name VARCHAR(255),
   city VARCHAR(255),
   country VARCHAR(255),
   address VARCHAR(255),
   description TEXT,
   capacity INT
);
