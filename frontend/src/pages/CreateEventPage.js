import React, {useState,useEffect} from 'react';
import { Dropdown } from 'react-bootstrap';
const CreateEventPage = () => {
    const [event, setEvent] = useState({
        userId:'',
        eventName: '',
        eventType: '',
        eventDate: '',
        eventStartTime: '',
        eventEndTime: '',
        eventDescription: '',
        venueId: ''
    });
    const [venues, setVenues] = useState([]);

    useEffect(() => {
        fetchVenues();
    }, []);

    const fetchVenues = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/venues');
            if (response.ok) {
                const data = await response.json();
                console.log(data);
                setVenues(data);
            } else {
                console.log('Failed to fetch venues');
            }
        } catch (error) {
            console.error('Error fetching venues:', error);
        }
    };
    const handleChange = (name, value) => {
        setEvent(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch('http://localhost:8080/api/events/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(event)
            });
            if (response.ok) {
                alert('Event created successfully!');
            } else {
            const errorMessage = await response.text();
            alert(`Failed to create event: ${errorMessage}`);
        }
    } catch (error) {
        console.error('Error creating event:', error);
        alert('Failed to create event');
    }
    };

    return (
        <div className="container">
            <div className="d-flex justify-content-center">
                <div>
                    <h1>Create Event</h1>
                    <form onSubmit={handleSubmit}>
                        <div className="mb-3">
                            <label htmlFor="eventName" className="form-label">User Id</label>
                            <input type="text" className="form-control input-box" id="userId" name="userId"
                                   value={event.userId}
                                   onChange={(e) => handleChange("userId", e.target.value)}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventName" className="form-label">Event Name</label>
                            <input type="text" className="form-control input-box" id="eventName" name="eventName"
                                   value={event.eventName}
                                   onChange={(e) => handleChange("eventName", e.target.value)}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventType" className="form-label">Event Type</label>
                            <input type="text" className="form-control input-box" id="eventType" name="eventType"
                                   value={event.eventType}
                                   onChange={(e) => handleChange("eventType", e.target.value)}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventDate" className="form-label">Event Date</label>
                            <input type="date" className="form-control input-box" id="eventDate" name="eventDate"
                                   value={event.eventDate}
                                   onChange={(e) => handleChange("eventDate", e.target.value)}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventTime" className="form-label">Event Start Time</label>
                            <input type="time" className="form-control input-box" id="eventStartTime"
                                   name="eventStartTime"
                                   value={event.eventStartTime}
                                   onChange={(e) => handleChange("eventStartTime", e.target.value)}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventTime" className="form-label">Event End Time</label>
                            <input type="time" className="form-control input-box" id="eventEndTime" name="eventEndTime"
                                   value={event.eventEndTime}
                                   onChange={(e) => handleChange("eventEndTime", e.target.value)}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="venueId" className="form-label">Venue</label>
                            <Dropdown>
                                <Dropdown.Toggle variant="primary" id="dropdown-venue">
                                    {event.venueId ? venues.find(v => v.venueId === event.venueId)?.venueName || 'Select a venue' : 'Select a venue'}
                                </Dropdown.Toggle>
                                <Dropdown.Menu>
                                    {venues.map(venue => (
                                        <Dropdown.Item key={venue.venueId}
                                                       onClick={() => handleChange("venueId", venue.venueId)}>
                                            {venue.venueName}
                                        </Dropdown.Item>
                                    ))}
                                </Dropdown.Menu>
                            </Dropdown>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventDescription" className="form-label">Event Description</label>
                            <textarea className="form-control input-description" id="eventDescription"
                                      name="eventDescription"
                                      value={event.eventDescription}
                                      onChange={(e) => handleChange("eventDescription", e.target.value)}/>
                        </div>
                        <div className="text-center mt-3">
                            <button type="submit" className="btn btn-primary">Create Event</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    )
}
export default CreateEventPage;