import React from 'react';
import EventSlider from "../components/EventSlider";

const HomePage =()=>{


    return(
        <div className= "container">
            <div className='upcoming-events'>
                <h2 className="mb-4">Upcoming Events</h2>
                <EventSlider timeFrame="nextMonth"/>
            </div>


        </div>
    );

};

export default HomePage;