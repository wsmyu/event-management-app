package org.humber.project.exceptions;

public class VenueNotFoundException extends RuntimeException {

    public VenueNotFoundException(String message) {
        super(message);
    }
}
