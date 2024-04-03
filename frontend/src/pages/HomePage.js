import React from 'react';
import EventSlider from "../components/EventSlider";
import {Link} from "react-router-dom";

const HomePage =()=>{


    return(
        <div className="container">
            <div className="banner">
                <h1 className="banner-text">Host Your Perfect Event with Us!</h1>
                <h2 className="banner-subheading">Create Memorable Experiences, Hassle-Free</h2>
                <Link to="/create-event" className="banner-btn btn btn-outline-light">Get Started Now</Link>
            </div>
            <div className='upcoming-events'>
                <h2 >Upcoming Events</h2>
                <EventSlider timeFrame="thisYear"/>


            </div>


        </div>
    );

};

export default HomePage;