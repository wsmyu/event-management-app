CREATE TABLE bookings (
  booking_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  event_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  venue_id BIGINT NOT NULL,
  booking_creation_date DATE NOT NULL,
  booking_start_time TIME NOT NULL,
  booking_end_time TIME NOT NULL,
  FOREIGN KEY (event_id) REFERENCES events (event_id),
  FOREIGN KEY (user_id) REFERENCES users (user_id),
  FOREIGN KEY (venue_id) REFERENCES venues (venue_id)
);
