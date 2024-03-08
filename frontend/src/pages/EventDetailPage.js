import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
const EventDetailPage = () => {
    const {eventId} = useParams();
    const [event, setEvent] = useState({
        userId: '',
        eventName: '',
        eventType: '',
        eventDate: '',
        eventStartTime: '',
        eventEndTime: '',
        eventDescription: '',
    });
    const formatDate = (dateArray) => {
        const year = dateArray[0];
        const month = dateArray[1].toString().padStart(2, '0');
        const day = dateArray[2].toString().padStart(2, '0');
        return `${year}-${month}-${day}`;
    };

    const formatTime = (timeArray) => {
        // Extract hours and minutes from the time array
        const hours = timeArray[0].toString().padStart(2, '0'); // Ensure two digits for hours
        const minutes = timeArray[1].toString().padStart(2, '0'); // Ensure two digits for minutes
        return `${hours}:${minutes}`;
    }
    const fetchEvent = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/events/${eventId}`);
            if (response.ok) {
                const eventData = await response.json();
                //Format the event date and time
                eventData.eventDate = formatDate(eventData.eventDate);
                eventData.eventStartTime = formatTime(eventData.eventStartTime);
                eventData.eventEndTime = formatTime(eventData.eventEndTime);
                setEvent(eventData);
            } else {
                console.log(event);
                console.error('Failed to fetch event details');
            }
        } catch (error) {
            console.error('Error fetching event details:', error);
        }
    };

    useEffect(() => {
        fetchEvent();
    }, []);

    return (
        <div className="container">
            {event && event.eventName != ""? (
                <div>
                    <h1 className="mt-5">Event Detail</h1>
                    <div className="card mt-3">
                        <div className="card-body">
                            <h5 className="card-title">Event ID: {event.eventId}</h5>
                            <p className="card-text">User ID: {event.userId}</p>
                            <p className="card-text">Event Name: {event.eventName}</p>
                            <p className="card-text">Event Type: {event.eventType}</p>
                            <p className="card-text">Event Date: {event.eventDate}</p>
                            <p className="card-text">Event Start Time: {event.eventStartTime}</p>
                            <p className="card-text">Event End Time: {event.eventEndTime}</p>
                            <p className="card-text">Event Description: {event.eventDescription}</p>
                        </div>
                    </div>
                </div>
            ) : (
                <div>
                    <h1>Event Not Found</h1>
                </div>
            )}
        </div>
    )}


export default EventDetailPage;
