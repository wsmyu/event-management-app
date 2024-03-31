import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';
import EventCard from "../components/EventCard";
import CloseButton from 'react-bootstrap/CloseButton';

const SearchResultPage = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const searchParams = new URLSearchParams(location.search);
    const eventName = searchParams.get('eventName');
    const [cities, setCities] = useState([]);
    const [events, setEvents] = useState([]);
    const [query, setQuery] = useState('');
    const [selectedCity, setSelectedCity] = useState('');
    const [selectedDate, setSelectedDate] = useState('');
    const [selectedType, setSelectedType] = useState('');
    const [navigateUrl, setNavigateUrl] = useState(`/search?eventName=${eventName}`);


    const eventTypes = [
        "Conference",
        "Exhibition",
        "Networking Event",
        "Seminar",
        "Trade Fair",
        "Workshop"
    ];

    const clearFilters = () => {
        setSelectedCity('');
        setSelectedDate('');
        setSelectedType('');
    };
    const fetchResult = async () => {
        let url = `http://localhost:8080/api/events/search?eventName=${eventName}`;

        // Append query parameters if they exist
        if (query) {
            url += `${query}`;
        }
        console.log("Fetching URL:", url);

        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('Failed to fetch events');
            }
            const events = await response.json();
            setEvents(events);
        } catch (error) {
            console.error('Error filtering events:', error);
        }
    }

    useEffect(() => {
        const constructFilterQueryAndUrl = () => {
            let queryString = '';

            if (selectedCity) {
                queryString += `&city=${selectedCity}`;
            }
            if (selectedDate) {
                queryString += `&date=${selectedDate}`;
            }
            if (selectedType) {
                queryString += `&eventType=${selectedType}`;
            }

            setQuery(queryString);

            // Update the navigateUrl by appending the query string
            setNavigateUrl(`/search?eventName=${eventName}${queryString}`);
            navigate(navigateUrl);

        };
        constructFilterQueryAndUrl();

    }, [selectedCity, selectedDate, selectedType, navigateUrl]);

    useEffect(() => {
        const fetchVenueCity = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/venues');
                if (response.ok) {
                    const venues = await response.json();
                    const uniqueCities = [...new Set(venues.map(venue => venue.city))];
                    setCities(uniqueCities);
                } else {
                    console.error('Failed to fetch venues');
                }
            } catch (error) {
                console.error('Error fetching venues:', error);
            }
        };

        fetchResult();
        fetchVenueCity();
    }, [query, eventName]);
    return (

        <div className="container">
            <div className="d-flex gap-3 justify-content-center align-items-center">
                <DropdownButton id="dropdown-basic-button" title="Filter by City">
                    {
                        cities.map((city, index) => (
                            <Dropdown.Item key={index}
                                           onClick={() => setSelectedCity(city)}>{city}</Dropdown.Item>
                        ))
                    }
                </DropdownButton>
                <DropdownButton id="dropdown-basic-button" title="Filter by Date">
                    <Dropdown.Item onClick={() => setSelectedDate('thisMonth')}>This Month</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSelectedDate('nextMonth')}>Next Month</Dropdown.Item>
                    <Dropdown.Item onClick={() => setSelectedDate('thisYear')}>This Year</Dropdown.Item>
                </DropdownButton>
                <DropdownButton id="dropdown-basic-button" title="Filter by Event Type">
                    {
                        eventTypes.map((type, index) => (
                            <Dropdown.Item key={index} onClick={() => setSelectedType(type)}>{type}</Dropdown.Item>
                        ))
                    }
                </DropdownButton>
                <CloseButton onClick={clearFilters}/>
            </div>

            <div className="row mt-3">
                {events.map((event, index) => (
                    <div key={index} className="col-3 mb-3">
                        <EventCard event={event}/>
                    </div>
                ))}
            </div>


        </div>
    )
}

export default SearchResultPage;