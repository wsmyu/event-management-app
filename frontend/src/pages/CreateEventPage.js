import React, {useState} from 'react';

const CreateEventPage = () => {
    const [event, setEvent] = useState({
        userId:'',
        eventName: '',
        eventType: '',
        eventDate: '',
        eventTime: '',
        eventDescription: ''
    });

    const handleChange = (e) => {
        const {name, value} = e.target;
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
                console.log('Failed to create event');
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
                                   onChange={handleChange}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventName" className="form-label">Event Name</label>
                            <input type="text" className="form-control input-box" id="eventName" name="eventName"
                                   value={event.eventName}
                                   onChange={handleChange}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventType" className="form-label">Event Type</label>
                            <input type="text" className="form-control input-box" id="eventType" name="eventType"
                                   value={event.eventType}
                                   onChange={handleChange}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventDate" className="form-label">Event Date</label>
                            <input type="date" className="form-control input-box" id="eventDate" name="eventDate"
                                   value={event.eventDate}
                                   onChange={handleChange}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventTime" className="form-label">Event Time</label>
                            <input type="time" className="form-control input-box" id="eventTime" name="eventTime"
                                   value={event.eventTime}
                                   onChange={handleChange}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="eventDescription" className="form-label">Event Description</label>
                            <textarea className="form-control input-description" id="eventDescription"
                                      name="eventDescription"
                                      value={event.eventDescription} onChange={handleChange}/>
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