package org.humber.project.exceptions;

public class GuestValidationException extends RuntimeException {

    private final ErrorCode errorCode;

    public GuestValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
