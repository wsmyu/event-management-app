package org.humber.project.exceptions;

public class GuestNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public GuestNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
