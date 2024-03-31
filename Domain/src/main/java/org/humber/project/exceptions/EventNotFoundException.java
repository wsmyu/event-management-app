package org.humber.project.exceptions;

public class EventNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public EventNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
