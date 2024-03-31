import React from 'react';
import EventSlider from "../components/EventSlider";

const HomePage =()=>{

    return(
        <div className= "container">
            <h1 className="mb-4">Upcoming Events</h1>
            <EventSlider timeFrame="thisYear"/>
        </div>
    );

};

export default HomePage;