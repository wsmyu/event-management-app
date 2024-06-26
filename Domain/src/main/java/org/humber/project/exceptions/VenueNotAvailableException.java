package org.humber.project.exceptions;

public class VenueNotAvailableException extends RuntimeException{
    private final ErrorCode errorCode;

    public VenueNotAvailableException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
