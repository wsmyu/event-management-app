import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
const EventDetailPage = () => {
    const { eventId } = useParams();
    const [event, setEvent] = useState({
        eventId: '',
        userId: '',
        eventName: '',
        eventType: '',
        eventDate: '',
        eventTime: '',
        eventDescription: ''
    });

    const fetchEvent = async () => {
        //testing to fetch the detail of event with id=6
        await fetch(`http://localhost:8080/api/events/${eventId}`)
            .then((response) => response.json())
            .then((data)=>setEvent(data))
            .catch((error)=>console.error('Error fetching events:', error))

    };

    useEffect(() => {
        fetchEvent();
    }, []);

    return (
        <div className="container">
            <h1 className="mt-5">Event Detail</h1>
            <div className="card mt-3">
                <div className="card-body">
                    <h5 className="card-title">Event ID: {event.eventId}</h5>
                    <p className="card-text">User ID: {event.userId}</p>
                    <p className="card-text">Event Name: {event.eventName}</p>
                    <p className="card-text">Event Type: {event.eventType}</p>
                    <p className="card-text">Event Date: {event.eventDate}</p>
                    <p className="card-text">Event Time: {event.eventTime}</p>
                    <p className="card-text">Event Description: {event.eventDescription}</p>
                </div>
            </div>
        </div>
    );
};

export default EventDetailPage;
