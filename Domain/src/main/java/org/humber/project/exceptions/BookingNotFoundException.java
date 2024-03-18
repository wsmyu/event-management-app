package org.humber.project.exceptions;

public class BookingNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
    public BookingNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }


}
