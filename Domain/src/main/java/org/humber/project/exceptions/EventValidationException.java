package org.humber.project.exceptions;


public class EventValidationException extends RuntimeException {

    private final ErrorCode errorCode;

    public EventValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
