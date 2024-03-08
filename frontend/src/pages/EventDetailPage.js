import React, { useEffect, useState } from 'react';
import { useParams , useNavigate } from 'react-router-dom';
import {Button} from "react-bootstrap";
import Modal from "react-bootstrap/Modal";
import CustomToast from "../components/CustomToast";
const EventDetailPage = () => {
    const {eventId} = useParams();
    const navigate = useNavigate();
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [toastMessage, setToastMessage] = useState('');
    const [toastVariant, setToastVariant] = useState('success');
    const [showToast, setShowToast] = useState(false);
    const [budget, setBudget] = useState(null);

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
    const fetchEventAndBudget = async () => {
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
            // Fetch budget details
            const budgetResponse = await fetch(`http://localhost:8080/api/events/${eventId}/budget`);
            if (budgetResponse.ok) {
                const budgetData = await budgetResponse.json();
                setBudget(budgetData);
            } else {
                console.error('Failed to fetch budget details');
            }
        } catch (error) {
            console.error('Error fetching event details:', error);
        }
    };

    const showSuccessMessage = (message) => {
        setShowToast(true);
        setToastVariant('success');
        setToastMessage(message);
    };
    const handleDelete = async () => {
        try {
            setShowDeleteModal(false);
            const response = await fetch(`http://localhost:8080/api/events/${eventId}/delete`, {
                method: 'DELETE',
            });
            if (response.ok) {
                // Scroll to the top of the page
                window.scrollTo(0, 0);
                showSuccessMessage('Event deleted successfully!');
                // Delay navigation after showing the toast
                setTimeout(() => {
                    navigate('/');
                }, 3000);

            } else {
                const errorMessage = await response.text();
                alert(`Failed to delete event: ${errorMessage}`);
            }
        } catch (error) {
            console.error('Error deleting event:', error);
        }
    };

    useEffect(() => {
        fetchEventAndBudget();
    }, [eventId]);

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
                         {budget && (
                                        <div className="card mt-3">
                                            <div className="card-header">Budget Details</div>
                                            <div className="card-body">
                                                <p>Venue Cost: ${budget.venueCost}</p>
                                                <p>Beverage Cost Per Person: ${budget.beverageCostPerPerson}</p>
                                                <p>Guest Number: {budget.guestNumber}</p>
                                                <p>Total Budget: ${budget.totalBudget}</p>
                                            </div>
                                        </div>
                                    )}
                        </div>

                        <div className="text-center m-3">
                            <Button variant="primary"
                                    onClick={() => navigate(`/event/${eventId}/update`)}>Update Event</Button>
                            <Button className="btn btn-danger" onClick={() => setShowDeleteModal(true)}>
                                Delete Event
                            </Button>
                            <Button variant="secondary" onClick={() => navigate(`/event/${eventId}/budget-management`)}>Manage Budget</Button>

                        </div>
                    </div>
                    {/*Success Toast */}
                    <CustomToast
                        showToast={showToast}
                        setShowToast={setShowToast}
                        toastVariant={toastVariant}
                        toastMessage={toastMessage}
                    />
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
                </div>
            ) : (
                <div>
                    <h1>Event Not Found</h1>
                </div>
            )}
        </div>
    )}


export default EventDetailPage;
