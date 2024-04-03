import React from 'react';

const EventForm = ({ event, handleChange, handleSubmit, buttonText }) => {
    const eventTypes = [
        "Conference",
        "Exhibition",
        "Networking Event",
        "Seminar",
        "Trade Fair",
        "Workshop"
    ];
    return (
        <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="eventName" className="form-label">Event Name</label>
                    <input type="text" className="form-control input-box" id="eventName" name="eventName"
                           value={event.eventName}
                           onChange={(e) => handleChange("eventName", e.target.value)}/>
                </div>
            <div className="mb-3">
                <label htmlFor="eventType" className="form-label">Event Type</label>
                <select className="form-select input-box" id="eventType" name="eventType"
                        value={event.eventType}
                        onChange={(e) => handleChange("eventType", e.target.value)}>
                    <option value="">Select Event Type</option>
                    {eventTypes.map((eventType, index) => (
                        <option key={index} value={eventType}>{eventType}</option>
                    ))}

                </select>
            </div>
            <div className="mb-3">
                <label htmlFor="eventDate" className="form-label">Event Date</label>
                <input type="date" className="form-control input-box" id="eventDate" name="eventDate"
                       value={event.eventDate}
                       onChange={(e) => handleChange("eventDate", e.target.value)}/>
            </div>
            <div className="mb-3">
            <label htmlFor="eventStartTime" className="form-label">Event Start Time</label>
                    <input type="time"  className="form-control input-box" id="eventStartTime"
                           name="eventStartTime"
                           value={event.eventStartTime}
                           onChange={(e) => handleChange("eventStartTime", e.target.value)}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="eventEndTime" className="form-label">Event End Time</label>
                    <input type="time"  className="form-control input-box" id="eventEndTime" name="eventEndTime"
                           value={event.eventEndTime}
                           onChange={(e) => handleChange("eventEndTime", e.target.value)}/>
                </div>

                <div className="mb-3">
                    <label htmlFor="eventDescription" className="form-label">Event Description</label>
                    <textarea className="form-control input-description" id="eventDescription"
                              name="eventDescription"
                              value={event.eventDescription}
                              onChange={(e) => handleChange("eventDescription", e.target.value)}/>
                </div>
                <div className="text-center mt-3">
                    <button type="submit" className="custom-button">{buttonText}</button>
                </div>

            </form>


    );
};

export default EventForm;
