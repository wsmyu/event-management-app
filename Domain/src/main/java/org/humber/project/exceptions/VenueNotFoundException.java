package org.humber.project.exceptions;

public class VenueNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;

    public VenueNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
