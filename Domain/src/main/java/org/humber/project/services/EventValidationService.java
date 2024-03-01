package org.humber.project.services;


import org.humber.project.domain.Event;

public interface EventValidationService {
    void validateEvent(Event event);
}
