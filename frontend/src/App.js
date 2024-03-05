import React from 'react';
import {Route, Routes } from 'react-router-dom';
import EventDetailPage from "./pages/EventDetailPage";
import CreateEventPage from "./pages/CreateEventPage";
import Header from "./components/Header";
import "./App.css";
import VenueBookingPage from "./pages/VenueBookingPage";
function App() {
    return (

            <div className="App">
                <Header />
                <Routes>
                    <Route path="/event/:id" element={<EventDetailPage />} />
                    <Route path="/create-event" element={<CreateEventPage />} />
                    <Route path="/venue-booking" element={<VenueBookingPage />} />
                </Routes>
            </div>

    );
}

export default App;