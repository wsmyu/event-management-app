import React, {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import { Toast } from 'react-bootstrap';
import {Button} from "react-bootstrap";
import ToastContainer from 'react-bootstrap/ToastContainer';
import EventForm from "../components/EventForm";
import CustomToast from "../components/CustomToast";
const CreateEventPage = () => {
    const [event, setEvent] = useState({
        userId:'',
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
    const showSuccessMessage = (message) => {
        setShowToast(true);
        setToastVariant('success');
        setToastMessage(message);

        // Hide the toast after 5 seconds (5000 milliseconds)
        setTimeout(() => {
            setShowToast(false);
        }, 4000);
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
                showSuccessMessage('Event created successfully!');
                //Clear the form after submitting
                setEvent({
                    userId:'',
                    eventName: '',
                    eventType: '',
                    eventDate: '',
                    eventStartTime: '',
                    eventEndTime: '',
                    eventDescription: '',
                    venueId: ''
                });
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
                <div>
                    <CustomToast
                        showToast={showToast}
                        setShowToast={setShowToast}
                        toastVariant={toastVariant}
                        toastMessage={toastMessage}
                    />
                    <h1>Create Event</h1>
                    <EventForm
                        event={event}
                        handleChange={handleChange}
                        handleSubmit={handleSubmit}
                        buttonText="Create Event"
                    />
                </div>
            </div>
        </div>
    )
}
export default CreateEventPage;