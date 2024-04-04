package org.humber.project.exceptions;

public class BudgetNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public BudgetNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
