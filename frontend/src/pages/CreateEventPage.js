import React, {useState} from 'react';
import EventForm from "../components/EventForm";
import CustomToast from "../components/CustomToast";
import SuccessPage from "./SuccessPage";
import {useUser} from "../components/UserContext";

const CreateEventPage = () => {
    const [event, setEvent] = useState({
        userId: '',
        eventName: '',
        eventType: '',
        eventDate: '',
        eventStartTime: '',
        eventEndTime: '',
        eventDescription: '',
    });
    const [eventId, setEventId] = useState('');
    const [toastMessage, setToastMessage] = useState('');
    const [toastVariant, setToastVariant] = useState('success');
    const [showToast, setShowToast] = useState(false);
    const [showSuccessPage, setShowSuccessPage] = useState(false);
    const [validationErrors, setValidationErrors] = useState({});
    const loggedInUser = useUser().loggedInUser;
    const showSuccessMessage = (message) => {
        setShowToast(true);
        setToastVariant('success');
        setToastMessage(message);

        // Hide the toast after 5 seconds (5000 milliseconds)
        setTimeout(() => {
            setShowToast(false);
        }, 2000);
    };

    const handleChange = (name, value) => {
        setEvent(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        // Define an array of field names
        const requiredFields = ['eventName', 'eventType', 'eventDate', 'eventStartTime', 'eventEndTime', 'eventDescription'];

        // Validation logic
        const errors = {};
        requiredFields.forEach(field => {
            if (!event[field]) {
                errors[field] = `${field.charAt(0).toUpperCase()+ field.split(/(?=[A-Z])/).join(' ').slice(1)} is required`;
            }
        });

        if (Object.keys(errors).length > 0) {
            setValidationErrors(errors);
            return;
        } else {
            setValidationErrors({});
        }
        try {
            // Modify the event object to include the userId
            const eventWithUserId = {
                ...event,
                userId: loggedInUser.userId
            };

            const response = await fetch('http://localhost:8080/api/events', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(eventWithUserId)
            });

            if (!response) {
                console.error('No response received');
            }

            if (!response.ok) {
                console.error('Network response was not ok');
            }

            if (response.ok) {
                // Scroll to the top of the page
                const responseBody = await response.json();
                setEventId(responseBody.eventId);
                window.scrollTo(0, 0);
                showSuccessMessage('Event created successfully!');
                setTimeout(() => {
                        setShowSuccessPage(true)
                    }, 3000
                )
            } else {
                const errorMessage = await response.text();
                setToastVariant('danger');
                setToastMessage(`Failed to create event: ${errorMessage}`);
                setShowToast(true);
            }
        } catch (error) {
            console.error('Error creating event:', error);
            alert('Failed to create event');
        }
    };

    return (
        <div className="container">
            <div className="d-flex justify-content-center">
                {loggedInUser ? (
                    // Show create event form if user is logged in
                    !showSuccessPage && (
                        <div>
                            <CustomToast
                                showToast={showToast}
                                setShowToast={setShowToast}
                                toastVariant={toastVariant}
                                toastMessage={toastMessage}
                                testID="toast-message"

                            />
                            <h1>Create Event</h1>
                            <EventForm
                                event={event}
                                handleChange={handleChange}
                                handleSubmit={handleSubmit}
                                buttonText="Create Event"
                                testID="create-event-button"
                                validationErrors={validationErrors}
                            />
                        </div>
                    )
                ) : (
                    <div>
                        <p>Please login first to create an event.</p>
                    </div>
                )}

                {showSuccessPage && (
                    <SuccessPage eventId={eventId} message="Event created successfully" />
                )}
            </div>
        </div>
    );
}
export default CreateEventPage;