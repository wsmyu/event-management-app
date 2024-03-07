import React, {useState, useEffect} from 'react';
import {useParams,useNavigate} from 'react-router-dom';
import {Form, Button} from 'react-bootstrap';
import EventForm from "../components/EventForm";
import Modal from 'react-bootstrap/Modal';
import CustomToast from "../components/CustomToast";
const UpdateEventPage = () => {
    const {eventId} = useParams();
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
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [toastMessage, setToastMessage] = useState('');
    const [toastVariant, setToastVariant] = useState('success');
    const [showToast, setShowToast] = useState(false);

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

    // Function to format date as yyyy-MM-dd
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
                showSuccessMessage('Event updated successfully!');
            } else {
                const errorMessage = await response.text();
                alert(`Failed to update event: ${errorMessage}`);
            }
        } catch (error) {
            console.error('Error updating event:', error);
        }
    };

    const showSuccessMessage = (message) => {
        setShowToast(true);
        setToastVariant('success');
        setToastMessage(message);

        setTimeout(() => {
            setShowToast(false);
        }, 4000);
    };
    const handleDelete = async () => {
        try {
            setShowDeleteModal(false);
            const response = await fetch(`http://localhost:8080/api/events/${eventId}/delete`, {
                method: 'DELETE',
            });
            if (response.ok) {
                showSuccessMessage('Event deleted successfully!');
                // Delay navigation after showing the toast
                setTimeout(() => {
                    navigate('/');
                }, 4000);

            } else {
                const errorMessage = await response.text();
                alert(`Failed to delete event: ${errorMessage}`);
            }
        } catch (error) {
            console.error('Error deleting event:', error);
        }
    };

    return (
        <div className="container">
            <div className="d-flex justify-content-center">
                <div>
                    <h1>Update Event</h1>
                    {event && (
                        <>
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
                            <div className="text-center mt-3">
                                <button className="btn btn-danger" onClick={() => setShowDeleteModal(true)}>
                                    Delete Event
                                </button>
                            </div>
                            {/* Delete Event Modal */}
                            <Modal show={showDeleteModal} onHide={() => setShowDeleteModal(false)}>
                                <Modal.Header closeButton>
                                    <Modal.Title>Delete Event</Modal.Title>
                                </Modal.Header>
                                <Modal.Body>
                                    Are you sure you want to delete this event?
                                </Modal.Body>
                                <Modal.Footer>
                                    <Button variant="secondary" onClick={() => setShowDeleteModal(false)}>Close</Button>
                                    <Button variant="danger" onClick={handleDelete}>Delete</Button>
                                </Modal.Footer>
                            </Modal>
                        </>
                    )}
                </div>

            </div>
        </div>
    );
};

export default UpdateEventPage;
