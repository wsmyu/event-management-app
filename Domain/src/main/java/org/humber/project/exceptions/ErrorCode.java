package org.humber.project.exceptions;

public enum ErrorCode {
    ENTITY_NOT_FOUND("Entity not found"),
    EVENT_NOT_FOUND("Event not found"),
    VENUE_NOT_FOUND("Venue not found"),
    BOOKING_NOT_FOUND("Booking not found"),
    VENUE_NOT_AVAILABLE("Venue is not available at the requested date and time"),
    INVALID_EVENT_DATE("Event date must in the future"),
    INVALID_EVENT_TIME("Event end time must after event start time"),
    INVALID_BOOKING_DATE("Booking date must in the future"),
    INVALID_BOOKING_TIME("Booking end time must after booking start time"),
    BOOKING_DATE_TIME_MISMATCH("Booking date and time do not match event date and time"),
    INVALID_GUEST_DETAILS("Invalid guest details provided"),
    INVALID_EVENT_ID("Invalid event ID provided"),
    GUEST_NOT_FOUND("No guests found for the provided event ID"); // New error code

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}