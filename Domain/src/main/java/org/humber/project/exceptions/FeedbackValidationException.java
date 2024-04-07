package org.humber.project.exceptions;

import java.util.List;
import java.util.stream.Collectors;

public class FeedbackValidationException extends RuntimeException {
    private final List<ErrorCode> errorCodes;

    public FeedbackValidationException(List<ErrorCode> errorCodes) {
        super(buildErrorMessage(errorCodes));
        this.errorCodes = errorCodes;
    }

    public List<ErrorCode> getErrorCodes() {
        return errorCodes;
    }

    private static String buildErrorMessage(List<ErrorCode> errorCodes) {
        return errorCodes.stream()
                .map(ErrorCode::getMessage)
                .collect(Collectors.joining(", "));
    }
}