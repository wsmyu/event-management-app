package org.humber.project.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;


public class EventValidationException extends RuntimeException {

    public EventValidationException(String message) {
        super(message);
    }

}
