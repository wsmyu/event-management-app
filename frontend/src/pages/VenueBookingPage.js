import React, {useState, useEffect} from 'react';
import {useParams} from "react-router-dom";
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import CustomToast from "../components/CustomToast";
import SuccessPage from "./SuccessPage";
import {useUser} from "../components/UserContext";


const VenueBookingPage = () => {
    const {eventId} = useParams();
    const [venues, setVenues] = useState([]);
    const [selectedVenue, setSelectedVenue] = useState(null);
    const [showBookingForm, setShowBookingForm] = useState(false);
    const [bookingDate, setBookingDate] = useState('');
    const [bookingStartTime, setBookingStartTime] = useState('');
    const [bookingEndTime, setBookingEndTime] = useState('');
    const [userId, setUserId] = useState('');
    const [toastMessage, setToastMessage] = useState('');
    const [toastVariant, setToastVariant] = useState('success');
    const [showToast, setShowToast] = useState(false);
    const [showSuccessPage,setShowSuccessPage]=useState(false);
    const loggedInUser = useUser().loggedInUser;

    const showSuccessMessage = (message) => {
        setShowToast(true);
        setToastVariant('success');
        setToastMessage(message);

        // Hide the toast after 5 seconds (5000 milliseconds)
        setTimeout(() => {
            setShowToast(false);
        }, 3000);
    };


    const imageBodyTemplate = (rowData) => {
        return <img src={rowData.imageUrl} alt={rowData.image} className="shadow-2 border-round"
                    style={{width: '10rem'}}/>;
    };

    const addressBodyTemplate = (rowData) => {
        return <span>{rowData.address}, {rowData.city}, {rowData.country}</span>
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (!selectedVenue) {
                console.error('No selected venue');
                return;
            }

            //Create a booking request
            const bookingRequest = {
                eventId: eventId,
                venueId: selectedVenue.venueId,
                bookingDate: bookingDate,
                bookingStartTime: bookingStartTime,
                bookingEndTime: bookingEndTime,
                userId: userId
            };

            const response = await fetch(`http://localhost:8080/api/bookings/${selectedVenue.venueId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(bookingRequest)

            });
            if (response.ok) {
                showSuccessMessage('Venue booked successfully!');
                setTimeout(()=>{
                    setShowSuccessPage(true);
                },3000)
                console.log("Venue booked successfully!");
            } else {
                const errorMessage = await response.text();
                setToastVariant('danger');
                setToastMessage(`Failed to book venue: ${errorMessage}`);
                setShowToast(true);
                console.error('Failed to book venue.', errorMessage);
            }
        } catch (error) {
            console.error('Error booking venue:', error);
        }
    };

    const handleNext = () => {
        setShowBookingForm(true);
    }

    useEffect(() => {
        const fetchVenues = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/venues');
                if (response.ok) {
                    const data = await response.json();
                    setVenues(data);
                } else {
                    console.log('Failed to fetch venues');
                }
            } catch (error) {
                console.error('Error fetching venues:', error);
            }
        };

        const fetchEventUserId = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/events/${eventId}`);
                if (response.ok) {
                    const eventData = await response.json();
                    setUserId(eventData.userId);
                } else {
                    console.error('Failed to fetch event details');
                }
            } catch (error) {
                console.error('Error fetching user id:', error);
            }
        };
        fetchVenues();
        fetchEventUserId();
    }, [eventId]);

    return (
        <div className="container">
            <CustomToast
                showToast={showToast}
                setShowToast={setShowToast}
                toastVariant={toastVariant}
                toastMessage={toastMessage}
            />
            {loggedInUser === null || loggedInUser.userId !== userId ? (
                <div className="d-flex justify-content-center">
                    <p>You are not permitted to access this page.</p>
                </div>
            ) : (
                <>
            {!showBookingForm && !showSuccessPage && (
                <div>
                    <h1>Book Venue </h1>
                    <div className="card">
                        {/* Step 1: Select Venue */}
                        <DataTable value={venues} selectionMode="single" selection={selectedVenue}
                                   onSelectionChange={(e) => setSelectedVenue(e.value)} removableSort
                                   tableStyle={{minWidth: '50rem'}}>
                            <Column selectionMode="single" headerStyle={{width: '3rem'}}></Column>
                            <Column field="venueName" header="Name" sortable style={{width: '25%'}}></Column>
                            <Column field="image" header="Image" body={imageBodyTemplate}></Column>
                            <Column field="description" header="Description" style={{width: '25%'}}></Column>
                            <Column header="Address" body={addressBodyTemplate} style={{width: '25%'}}></Column>
                            <Column field="capacity" header="Capacity" sortable style={{width: '25%'}}></Column>
                            <Column field="rating" header="Rating" sortable style={{width: '25%'}}></Column>
                        </DataTable>

                    </div>
                    <div className="d-flex justify-content-center m-3">
                        <button onClick={handleNext} className="btn btn-primary">Next</button>
                    </div>
                </div>
            )}

            {/* Step 2: Booking Form */}
            {showBookingForm && !showSuccessPage && (
                <div>
                    <h2>Booking Details</h2>
                    <div className="container">
                        <div className="d-flex justify-content-center">
                            <form>
                                <div className="mb-3">
                                    <label htmlFor="bookingDate">Booking Date : </label>
                                    <input type="date" id="bookingDate" value={bookingDate}
                                           className="form-control"
                                           onChange={(e) => setBookingDate(e.target.value)}
                                           required/>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="bookingStartTime">Booking Start Time:</label>
                                    <input
                                        type="time"
                                        id="bookingStartTime"
                                        className="form-control"
                                        value={bookingStartTime}
                                        onChange={(e) => setBookingStartTime(e.target.value)}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="bookingEndTime">Booking End Time:</label>
                                    <input
                                        type="time"
                                        id="bookingEndTime"
                                        className="form-control"
                                        value={bookingEndTime}
                                        onChange={(e) => setBookingEndTime(e.target.value)}
                                        required
                                    />
                                </div>
                            </form>
                        </div>
                    </div>
                    <div className="d-flex justify-content-center gap-2">
                        <button onClick={()=>setShowBookingForm(false)} className="btn btn-secondary">Back</button>
                        <button onClick={handleSubmit} className="btn btn-primary">Submit</button>
                    </div>
                </div>
            )}

            {/*Show success page*/}
            {showSuccessPage && (
               <SuccessPage eventId={eventId} message="Venue booked successfully" />
            )}
                </>
            )}
        </div>
    );

};
export default VenueBookingPage;


