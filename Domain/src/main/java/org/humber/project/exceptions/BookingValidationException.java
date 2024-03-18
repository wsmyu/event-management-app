package org.humber.project.exceptions;

public class BookingValidationException extends RuntimeException {
    private final ErrorCode errorCode;

    public BookingValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
