-- Drop the booking_creation_date column
ALTER TABLE bookings
DROP COLUMN booking_creation_date;

-- Add the created_at and updated_at columns
ALTER TABLE bookings
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
