import React from 'react';
import { Link } from 'react-router-dom';

function Footer() {
    return (
        <footer className="footer mt-auto py-3 bg-dark text-white">
            <div className="container">
                <div className="row">
                    {/* Events Column */}
                    <div className="col-md-4">
                        <h5>Events</h5>
                        <ul className="list-unstyled">
                            <li><Link to="/create-event" className="text-white">Create Event</Link></li>
                        </ul>
                    </div>
                    {/* Friends Column */}
                    <div className="col-md-4">
                        <h5>Friends</h5>
                        <ul className="list-unstyled">
                            <li><Link to="/user/friends" className="text-white">Friend List</Link></li>
                            <li><Link to="/user/request" className="text-white">Friend Requests</Link></li>
                        </ul>
                    </div>

                    <div className="col-md-4">
                        <h5>Others</h5>
                        <ul className="list-unstyled">
                            <li><Link to="/user/request" className="text-white">Feedback</Link></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div className="text-center">
                <span>Event Management App</span>
            </div>
        </footer>
    );
}

export default Footer;
