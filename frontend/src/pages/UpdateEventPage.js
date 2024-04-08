import React, {useState, useEffect} from 'react';
import {useParams, useNavigate} from 'react-router-dom';
import EventForm from "../components/EventForm";
import CustomToast from "../components/CustomToast";
import SuccessPage from "./SuccessPage";
import {formatDate, formatTime} from "../utils";

const UpdateEventPage = () => {
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
    const navigate = useNavigate();

    const [toastMessage, setToastMessage] = useState('');
    const [toastVariant, setToastVariant] = useState('success');
    const [showToast, setShowToast] = useState(false);
    const [showSuccessPage, setShowSuccessPage] = useState(false);

    useEffect(() => {
        // Fetch event details from the backend when the component mounts
        fetchEventDetails();
    }, []);

    const fetchEventDetails = async () => {
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
                console.error('Failed to fetch event details');
            }
        } catch (error) {
            console.error('Error fetching event details:', error);
        }
    };

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
        try {
            const response = await fetch(`http://localhost:8080/api/events/${eventId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(event)
            });
            if (response.ok) {
                // Scroll to the top of the page
                window.scrollTo(0, 0);
                showSuccessMessage('Event updated successfully!');
                setTimeout(() => {
                    setShowSuccessPage(true)
                }, 3000)
            } else {
                const errorMessage = await response.text();
                setToastVariant('danger');
                setToastMessage(`${errorMessage}`);
                setShowToast(true);
            }
        } catch (error) {
            console.error('Error updating event:', error);
        }
    };


    return (
        <div className="container">
            <div className="d-flex justify-content-center">
                <div>
                    {event && !showSuccessPage && (
                        <>
                            <h1>Update Event</h1>

                            {/* Success Toast */}
                            <CustomToast
                                showToast={showToast}
                                setShowToast={setShowToast}
                                toastVariant={toastVariant}
                                toastMessage={toastMessage}
                            />
                            <EventForm
                                event={event}
                                handleChange={handleChange}
                                handleSubmit={handleSubmit}
                                buttonText="Update Event"
                            />
                        </>
                    )}
                </div>
                {showSuccessPage && (
                    <SuccessPage eventId={eventId} message="Event updated successfully"/>
                )}

            </div>
        </div>
    );
};

export default UpdateEventPage;
