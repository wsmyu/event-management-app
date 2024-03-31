CREATE TABLE friends (
    friend_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    friend_user_id BIGINT,
    pending BOOLEAN DEFAULT true,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (friend_user_id) REFERENCES users(user_id)
);