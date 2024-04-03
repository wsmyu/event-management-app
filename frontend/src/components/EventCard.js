import React, {useEffect, useState} from 'react';
import Card from 'react-bootstrap/Card';
import {formatDate, formatTime, fetchVenueDetails} from "../utils";
import {FaRegCalendar} from "react-icons/fa";
import {IoTimeOutline} from "react-icons/io5";
import {BiCategory} from "react-icons/bi";
import {FaMapMarkerAlt} from "react-icons/fa";
import {GoNote} from "react-icons/go";
import {Link} from 'react-router-dom';

const EventCard = ({event}) => {
    const [formattedEventDate, setFormattedEventDate] = useState('')
    const [formattedStartTime, setFormattedStartTime] = useState('');
    const [formattedEndTime, setFormattedEndTime] = useState('');
    const [venue, setVenue] = useState('');

    useEffect(() => {
            //Fetch event venue
            const fetchVenue = async () => {
                try {
                    const venueData = await fetchVenueDetails(event.venueId);
                    setVenue(venueData);
                } catch (error) {
                    console.error('Error fetching venue:', error);
                }
            }

            fetchVenue();
            //Format date
            setFormattedEventDate(formatDate(event.eventDate));
            // Format start time
            setFormattedStartTime(formatTime(event.eventStartTime));
            // Format end time
            setFormattedEndTime(formatTime(event.eventEndTime));
        }, [event]
    );


    return (
        <div className="event-card">
            <Card >
                <Link to={`/event/${event.eventId}`} className="custom-link">
                    <Card.Body>
                        <Card.Title >{event.eventName}</Card.Title>
                        <Card.Text className="card-row">
                            <BiCategory/>
                            <span>{event.eventType}</span>
                        </Card.Text>
                        <Card.Text className="card-row">
                            <FaRegCalendar/>
                            <span>{formattedEventDate}</span>
                        </Card.Text>
                        <Card.Text className="card-row">
                            <IoTimeOutline/>
                            <span>{formattedStartTime} - {formattedEndTime}</span>
                        </Card.Text>
                        <Card.Text className="card-row">
                            <GoNote/>
                            <span className="event-description">{event.eventDescription}</span>
                        </Card.Text>

                        <Card.Text className="card-row">
                            <FaMapMarkerAlt/>
                            {venue != null ? (
                                <span>{venue.address}, {venue.city}, {venue.country}</span>) : (<span>To Be Confirmed</span>)}
                        </Card.Text>


                    </Card.Body>
                </Link>
            </Card>
        </div>
    );
};

export default EventCard;