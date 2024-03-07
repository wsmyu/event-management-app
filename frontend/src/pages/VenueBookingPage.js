import React, { useState, useEffect } from 'react';
import { Card, Button } from 'react-bootstrap';
import {useNavigate} from "react-router-dom";

const VenueBookingPage = ({ onSelectVenue }) => {
    const [venues, setVenues] = useState([]);
    const navigate = useNavigate();
    useEffect(() => {
        fetchVenues();
    }, []);

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

    return (
        <div className="container">
            <div className="row">
                {venues.map(venue => (
                    <div key={venue.venueId} className="col-md-4">
                        <Card style={{ width: '18rem' }} className="mb-4">
                            <Card.Body>
                                <Card.Title>{venue.venueName}</Card.Title>
                                <Card.Text>
                                    {venue.address}
                                </Card.Text>
                                <Button variant="primary" onClick={() => onSelectVenue(venue)}>Select</Button>
                            </Card.Body>
                        </Card>
                    </div>

                ))}
            </div>
            <div className="d-flex justify-content-center">
                <Button onClick={() => navigate('/create-event')} variant="secondary">Back to Event Creation</Button>
            </div>



        </div>
    );
};

export default VenueBookingPage;


