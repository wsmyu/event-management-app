import React, { useState } from 'react';

const CreateEventPage = () => {
    const [event, setEvent] = useState({
        eventName: '',
        eventType: '',
        eventDate: '',
        eventTime: '',
        eventDescription: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEvent(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();

    };

    return (
        <div className="container">
            <h1>Create Event</h1>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="eventName" className="form-label">Event Name</label>
                    <input type="text" className="form-control" id="eventName" name="eventName" value={event.eventName} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label htmlFor="eventType" className="form-label">Event Type</label>
                    <input type="text" className="form-control" id="eventType" name="eventType" value={event.eventType} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label htmlFor="eventDate" className="form-label">Event Date</label>
                    <input type="date" className="form-control" id="eventDate" name="eventDate" value={event.eventDate} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label htmlFor="eventTime" className="form-label">Event Time</label>
                    <input type="time" className="form-control" id="eventTime" name="eventTime" value={event.eventTime} onChange={handleChange} />
                </div>
                <div className="mb-3">
                    <label htmlFor="eventDescription" className="form-label">Event Description</label>
                    <textarea className="form-control" id="eventDescription" name="eventDescription" value={event.eventDescription} onChange={handleChange} />
                </div>
                <button type="submit" className="btn btn-primary">Create Event</button>
            </form>
        </div>
    );
};

export default CreateEventPage;
