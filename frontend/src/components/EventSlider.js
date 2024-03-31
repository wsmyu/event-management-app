import React, {useEffect, useRef, useState} from 'react';
import EventCard from "./EventCard";

const EventSlider = ({timeFrame}) => {
    const containerRef = useRef(null);
    const [scrollLeft, setScrollLeft] = useState(0);
    const [events, setEvents] = useState([]);

    const handleScroll = (scrollAmount) => {
        const newScrollLeft = scrollAmount + scrollLeft;
        containerRef.current.scrollTo({left: newScrollLeft, behavior: 'smooth'});
        setScrollLeft(newScrollLeft);
    }
    useEffect(() => {
        const fetchEvents = async (timeFrame) => {
            try {
                const response = await fetch(`http://localhost:8080/api/events/date?timeFrame=${timeFrame}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch events');
                }
                const data = await response.json();
                setEvents(data);

            } catch (error) {
                console.error('Error fetching events:', error);
            }
        };
        fetchEvents(timeFrame);
    }, [timeFrame])
    return (
        <div className="container">
                <div className="row">
                    <div className="col-1 d-flex align-items-center">
                        <button className="btn btn-dark rounded-circle"
                                onClick={() => handleScroll(-400)}>
                            &lt;
                        </button>
                    </div>
                    <div className="col-10">
                        <div className="row flex-nowrap overflow-hidden" ref={containerRef}>
                            {events.map((event, index) => (
                                <div key={index} className="col-4">
                                    <EventCard event={event}/>
                                </div>
                            ))}
                        </div>
                    </div>
                    <div className="col-1 d-flex align-items-center justify-content-end">
                        <button className="btn btn-dark rounded-circle" onClick={() => handleScroll(400)}>
                            &gt;
                        </button>
                    </div>

            </div>


        </div>
    );
};

export default EventSlider;