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
    USERNAME_ALREADY_REGISTERED("Username has been registered by the others"),
    REPEATED_FRIEND_REQUEST("This user is already your friend or friend request is pending"),
    INVALID_FRIEND_REQUEST("Invalid friend request"),
    INVALID_USERNAME("Username can not be empty"),
    INVALID_PASSWORD("Password can not be empty"),
    INVALID_FIRST_NAME("First name can not be empty"),
    INVALID_LAST_NAME("Last name can not be empty"),
    INVALID_EMAIL("Email can not be empty"),
    INVALID_RATING("Rating can not be empty"),
    INVALID_MESSAGE("Message can not be empty");
  
    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}