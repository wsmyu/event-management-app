import React, {useEffect, useState} from 'react';
import {useUser} from "../components/UserContext";
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import {formatDate, formatTime} from "../utils";
import { Link } from 'react-router-dom';
const UserEventPage = () => {
    const [events, setEvents] = useState();
    const {loggedInUser} = useUser();

    useEffect(() => {
        const fetchEvents = async () => {
            try {
                // Fetch event details
                const eventResponse = await fetch(`http://localhost:8080/api/events/user/${loggedInUser.userId}`);
                if (eventResponse.ok) {
                    const eventData = await eventResponse.json();
                    if (eventData && eventData.length > 0) {
                        // Process event data
                        eventData.forEach(event => {
                            event.eventDate = formatDate(event.eventDate);
                            event.eventStartTime = formatTime(event.eventStartTime);
                            event.eventEndTime = formatTime(event.eventEndTime);
                        });
                        console.log(eventData);
                        setEvents(eventData);
                    }
                }
            } catch (error) {
                console.error('Failed to fetch event data', error);
            }
        }
        fetchEvents();
    }, []);


    return (
        <div>
            <h1>My Events</h1>
            <div className="card mt-3">
                <DataTable value={events} tableStyle={{minWidth: '50rem'}}>
                    <Column sortable field="eventId" header="Event Id"></Column>
                    <Column sortable field="eventName" header="Event Name"></Column>
                    <Column sortable  field="eventType" header="EventType"></Column>
                    <Column field="eventDescription" header="Description"></Column>
                    <Column sortable field="eventDate" header="Date"></Column>
                    <Column sortable field="eventStartTime" header="Start Time"></Column>
                    <Column sortable field="eventEndTime" header="End Time"></Column>
                    <Column body={rowData => (
                        <Link to={`/event/${rowData.eventId}`}>
                            <button className="custom-button" >
                                <span className="p-button-text">View Details</span>
                            </button>
                        </Link>
                    )}></Column>
                </DataTable>
            </div>
        </div>
    );
}

export default UserEventPage;