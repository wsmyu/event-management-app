CREATE TABLE feedback (
    feedback_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    rating INT,
    message VARCHAR(1000),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
