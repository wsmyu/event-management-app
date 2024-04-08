CREATE TABLE guests (
    guest_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    invited_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    responded_at TIMESTAMP NULL DEFAULT NULL,
    response TEXT,
    CONSTRAINT fk_guest_event FOREIGN KEY (event_id) REFERENCES events(event_id),
    CONSTRAINT fk_guest_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);
