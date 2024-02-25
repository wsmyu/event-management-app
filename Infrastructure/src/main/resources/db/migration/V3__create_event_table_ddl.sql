CREATE TABLE events (
    event_id BIGINT  PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT  NOT NULL,
    venue_id BIGINT  NOT NULL,
    event_name VARCHAR(255),
    event_type VARCHAR(255),
    event_date DATE,
    event_time TIME,
    event_description TEXT,
    event_budget DECIMAL(10,2),
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (venue_id) REFERENCES venues (venue_id)
 );