package org.humber.project.services.impl;

import jakarta.inject.Inject;
import org.humber.project.domain.Event;
import org.humber.project.exceptions.ErrorCode;
import org.humber.project.exceptions.EventValidationException;
import org.humber.project.services.BookingService;
import org.humber.project.services.EventJPAService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceImplTest {
    @Mock
    private EventJPAService eventJPAService;
    @Mock
    private BookingService bookingService;

    @InjectMocks
    private EventServiceImpl eventService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        eventService = new EventServiceImpl(eventJPAService,bookingService, Arrays.asList(new EventDateValidation(), new EventTimeValidation()));
    }
    @Test
    public void testCreateEvent_Success(){
        // Initialize a complete Event object
        Event event = new Event();
        event.setEventId(1L);
        event.setUserId(2L);
        event.setVenueId(3L);
        event.setEventName("Test Event");
        event.setEventType("Conference");
        event.setEventDate(LocalDate.of(2025, 5, 30));
        event.setEventStartTime(LocalTime.of(9, 0));
        event.setEventEndTime(LocalTime.of(17, 0));
        event.setEventDescription("This is a sample event description.");

        when(eventJPAService.saveEvent(event)).thenReturn(event);

        Event result = eventService.createEvent(event);
        assertEquals(event, result);

        // Verify that the saveEvent method is called
        verify(eventJPAService).saveEvent(event);

    }

    @Test
    public void testCreateEvent_InvalidEventTimes() {
        // Set up the event with an invalid event finish time (before the event start time)
        Event eventWithInvalidTimes = new Event();  // Create a new event object for this test
        eventWithInvalidTimes.setEventStartTime(LocalTime.of(10, 0)); // Set start time
        eventWithInvalidTimes.setEventEndTime(LocalTime.of(8, 0)); // Set finish time (before start time)

        // Assert that the createEvent method throws the expected EventValidationException
        EventValidationException exception = assertThrows(EventValidationException.class, () -> eventService.createEvent(eventWithInvalidTimes));

        // Additional assertion on the thrown exception
        assertEquals(ErrorCode.INVALID_EVENT_TIME, exception.getErrorCode());
    }

    @Test
    public void testCreateEvent_InvalidEventDate() {
        // Set up the event with an invalid event date (e.g., a past date)
        Event eventWithInvalidDate = new Event();
        eventWithInvalidDate.setEventDate(LocalDate.of(2020,5,10));
        eventWithInvalidDate.setEventStartTime(LocalTime.of(9,0));
        eventWithInvalidDate.setEventEndTime(LocalTime.of(11,0));

        // Assert that the createEvent method throws the expected EventValidationException
        EventValidationException exception = assertThrows(EventValidationException.class,()->eventService.createEvent(eventWithInvalidDate));

        // Additional assertion on the thrown exception
        assertEquals(ErrorCode.INVALID_EVENT_DATE, exception.getErrorCode());
    }

    @Test
    public void testUpdateEvent_Success() {
        // Initialize an existing Event object
        Event existingEvent = new Event();
        existingEvent.setEventId(1L);
        existingEvent.setUserId(2L);
        existingEvent.setEventName("Existing Event");
        existingEvent.setEventType("Conference");
        existingEvent.setEventDate(LocalDate.of(2025, 5, 30));
        existingEvent.setEventStartTime(LocalTime.of(9, 0));
        existingEvent.setEventEndTime(LocalTime.of(17, 0));
        existingEvent.setEventDescription("This is an existing event description.");

        // Set up the update details
        Event updatedEvent = new Event();
        updatedEvent.setEventId(1L);
        updatedEvent.setUserId(2L);
        updatedEvent.setEventName("Updated Event");
        updatedEvent.setEventType("Workshop");
        updatedEvent.setEventDate(LocalDate.of(2025, 6, 1));
        updatedEvent.setEventStartTime(LocalTime.of(10, 0));
        updatedEvent.setEventEndTime(LocalTime.of(18, 0));
        updatedEvent.setEventDescription("This is an updated event description.");

        // Mock the behavior of findEventById to return the existing event
        when(eventJPAService.findEventById(existingEvent.getEventId())).thenReturn(existingEvent);

        // Mock the behavior of updateEventInfo to return the updated event
        when(eventService.updateEventInfo(existingEvent.getEventId(),updatedEvent)).thenReturn(updatedEvent);

        Event result = eventService.updateEventInfo(existingEvent.getEventId(),updatedEvent);
        assertEquals(updatedEvent, result);

    }

    @Test
    public void testRetrieveEventDetails_Success() {
        Long eventId = 1L;

        // Initialize an existing Event object
        Event existingEvent = new Event();
        existingEvent.setEventId(eventId);
        existingEvent.setUserId(2L);
        existingEvent.setVenueId(3L);
        existingEvent.setEventName("Existing Event");
        existingEvent.setEventType("Conference");
        existingEvent.setEventDate(LocalDate.of(2025, 5, 30));
        existingEvent.setEventStartTime(LocalTime.of(9, 0));
        existingEvent.setEventEndTime(LocalTime.of(17, 0));
        existingEvent.setEventDescription("This is an existing event description.");

        when(eventJPAService.findEventById(eventId)).thenReturn(existingEvent);

        Event result = eventService.retrieveEventDetails(eventId);
        assertEquals(existingEvent, result);

        // Verify that the findEventById method is called
        verify(eventJPAService).findEventById(eventId);
    }
    @Test
    public void testRetrieveEventDetails_InvalidEventId() {
        // Given an invalid event ID (null or non-existing event ID)
        Long invalidEventId = null;

        // Assert that the retrieveEventDetails method throws an EventNotFoundException for an invalid event ID
        EventValidationException exception = assertThrows(EventValidationException.class, () -> {
            eventService.retrieveEventDetails(invalidEventId);
        });

        assertEquals("Event not found", exception.getMessage());
    }


}