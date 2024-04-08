package org.humber.project.exceptions;

public class FriendValidationException extends RuntimeException {
    private final ErrorCode errorCode;

    public FriendValidationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
