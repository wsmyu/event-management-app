import React, {useEffect, useState} from 'react';
import {useParams, useNavigate} from 'react-router-dom';
import Modal from "react-bootstrap/Modal";
import CustomToast from "../components/CustomToast";
import Alert from 'react-bootstrap/Alert';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import {formatDate, formatTime} from "../utils";
import {useUser} from "../components/UserContext";
import Accordion from 'react-bootstrap/Accordion';

const EventDetailPage = () => {
    const {eventId} = useParams();
    const {loggedInUser} = useUser();
    const navigate = useNavigate();
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [toastMessage, setToastMessage] = useState('');
    const [toastVariant, setToastVariant] = useState('success');
    const [showToast, setShowToast] = useState(false);
    const [budget, setBudget] = useState(null);
    const [event, setEvent] = useState(null);
    const [venue, setVenue] = useState('');
    const [venueBooking, setVenueBooking] = useState('');
    const [loading, setLoading] = useState(true);
    const [venueBookingDateTimeMatch, setVenueBookingDateTimeMatch] = useState(true);

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
        console.log('logged in user:', loggedInUser)
        const fetchData = async () => {
            try {
                // Fetch event details
                const eventResponse = await fetch(`http://localhost:8080/api/events/${eventId}`);
                if (eventResponse.ok) {
                    const eventData = await eventResponse.json();
                    // Format the event date and time
                    eventData.eventDate = formatDate(eventData.eventDate);
                    eventData.eventStartTime = formatTime(eventData.eventStartTime);
                    eventData.eventEndTime = formatTime(eventData.eventEndTime);
                    setEvent(eventData);
                    setLoading(false);
                    // Set venue ID
                    const venueId = eventData.venueId;

                    // Fetch venue details
                    if (venueId != null) {
                        const venueResponse = await fetch(`http://localhost:8080/api/venues/${venueId}`);
                        if (venueResponse.ok) {
                            const venueData = await venueResponse.json();
                            setVenue(venueData);
                        } else {
                            console.error("Fail to fetch venue details")
                        }

                        //Fetch venue booking detail
                        const venueBookingResponse = await fetch(`http://localhost:8080/api/bookings/${eventId}`);
                        if (venueBookingResponse.ok) {
                            const venueBookingData = await venueBookingResponse.json();
                            venueBookingData.bookingDate = formatDate(venueBookingData.bookingDate);
                            venueBookingData.bookingStartTime = formatTime(venueBookingData.bookingStartTime);
                            venueBookingData.bookingEndTime = formatTime(venueBookingData.bookingEndTime);
                            setVenueBooking(venueBookingData);
                            checkVenueBookingDateTime();
                        } else {
                            console.error("Fail to fetch venue booking details")
                        }
                    }

                    // Fetch budget details
                    const budgetResponse = await fetch(`http://localhost:8080/api/events/${eventId}/budget`);
                    if (budgetResponse.ok) {
                        const budgetData = await budgetResponse.json();
                        setBudget(budgetData);
                    } else {
                        console.error('Failed to fetch budget details');
                    }

                } else {
                    setLoading(false);
                    console.error('Failed to fetch event details');
                }

            } catch (error) {
                console.error('Error fetching event and venue details:', error);
            }
        };
        const checkVenueBookingDateTime = () => {
            // Check if event and venueBooking are not null
            if (event && venueBooking) {
                // Check if venueBooking date and time match event date and time
                if (
                    venueBooking.bookingDate !== event.eventDate ||
                    venueBooking.bookingStartTime !== event.eventStartTime ||
                    venueBooking.bookingEndTime !== event.eventEndTime
                ) {
                    setVenueBookingDateTimeMatch(false);
                }
            }
        }

        fetchData();

    }, [eventId, event?.eventDate, event?.eventEndTime, event?.eventStartTime, venueBooking?.bookingDate, venueBooking?.bookingStartTime, venueBooking?.bookingEndTime]);


    return (
        <div className="container">
            {loading && (
                <div><h1>Loading...</h1></div>
            )}
            {!loading && event && event.eventName !== "" ? (
                <div>
                    <h1>Event Detail</h1>
                    <div className="card mt-3">
                        <div className="card-body">
                            <h5 className="card-title"><strong>Event ID: </strong>{event.eventId}</h5>
                            <p className="card-text"><strong>Event Creator id: </strong> {event.userId}</p>
                            <p className="card-text"><strong>Event Name:</strong> {event.eventName}</p>
                            <p className="card-text"><strong>Event Type:</strong> {event.eventType}</p>
                            <p className="card-text"><strong>Event Date: </strong>{event.eventDate}</p>
                            <p className="card-text"><strong>Event Start Time:</strong> {event.eventStartTime}</p>
                            <p className="card-text"><strong>Event End Time: </strong>{event.eventEndTime}</p>
                            <p className="card-text"><strong>Event Description: </strong>{event.eventDescription}</p>

                            {venue.venueId && loggedInUser !== null && loggedInUser.userId === event.userId && (
                                <div className="d-flex justify-content-center align-items-center">
                                    <Accordion className="w-100 mt-3 ">
                                        <Accordion.Item eventKey="0">
                                            <Accordion.Header>Venue Booking Detail</Accordion.Header>
                                            <Accordion.Body>
                                                <Row>
                                                    <Col md={4}>
                                                        <p><strong>Booking Date: </strong> {venueBooking.bookingDate}
                                                        </p>
                                                        <p><strong>Booking Start
                                                            Time: </strong>{venueBooking.bookingStartTime}</p>
                                                        <p><strong>Booking End
                                                            Time: </strong>{venueBooking.bookingEndTime}</p>
                                                        <p><strong>Venue: </strong>{venue.venueName}</p>
                                                        <p>
                                                            <strong>Address: </strong>{venue.address}, {venue.city}, {venue.country}
                                                        </p>
                                                    </Col>
                                                    <Col md={4}>
                                                        <img src={venue.imageUrl} className="rounded w-100 mb-3"
                                                             alt="venue image"/>
                                                    </Col>
                                                    <Col md={4}>
                                                        {!venueBookingDateTimeMatch && (
                                                            <Alert key="info" variant="info" className="w-100">
                                                                *Please ensure the booking date and time match the event
                                                                details
                                                            </Alert>
                                                        )}
                                                    </Col>
                                                </Row>
                                            </Accordion.Body>
                                        </Accordion.Item>
                                    </Accordion>
                                </div>
                            )}


                            {loggedInUser !== null && loggedInUser.userId === event.userId && budget && (
                                <Accordion className="mt-3">
                                    <Accordion.Item eventKey="0">
                                        <Accordion.Header>Budget Details</Accordion.Header>
                                        <Accordion.Body>
                                            <p>Venue Cost: ${budget.venueCost}</p>
                                            <p>Beverage Cost Per Person: ${budget.beverageCostPerPerson}</p>
                                            <p>Guest Number: {budget.guestNumber}</p>
                                            <p>Total Budget: ${budget.totalBudget}</p>
                                        </Accordion.Body>
                                    </Accordion.Item>
                                </Accordion>
                            )}

                            {
                                loggedInUser !== null && loggedInUser.userId === event.userId ? (
                                    <div className="d-flex justify-content-center m-3 gap-3 ">
                                        <button className="custom-button"
                                                onClick={() => navigate(`/event/${eventId}/update`)}>
                                            Update Event Info
                                        </button>
                                        <button className="custom-button"
                                                onClick={() => navigate(`/venue-booking/${eventId}`)}>
                                            {venue ? ("Change Venue Booking") : ("Book Venue")}
                                        </button>
                                        <button className="custom-button"
                                                onClick={() => navigate(`/event/${eventId}/budget-management`)}>
                                            Manage Budget
                                        </button>
                                        <button className="btn btn-outline-danger"
                                                onClick={() => setShowDeleteModal(true)}>
                                            Delete Event
                                        </button>
                                    </div>
                                ) : (
                                    <div className="d-flex justify-content-center m-3 ">
                                        <button className="custom-button">
                                            Join Event
                                        </button>
                                    </div>
                                )
                            }
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
                            <button className="btn btn-secondary" onClick={() => setShowDeleteModal(false)}>
                                Close
                            </button>
                            <button className="btn btn-danger" onClick={handleDelete}>
                                Delete
                            </button>
                        </Modal.Footer>
                    </Modal>
                </div>
            ) : (
                <div>
                    <h1>Event Not Found</h1>
                </div>
            )}
        </div>
    )
}

export default EventDetailPage;
