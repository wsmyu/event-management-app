import React from 'react';
import {Route, Routes } from 'react-router-dom';
import EventDetailPage from "./pages/EventDetailPage";
import CreateEventPage from "./pages/CreateEventPage";
import BudgetManagementPage from "./pages/BudgetManagementPage";
import Header from "./components/Header";
import "./App.css";
import VenueBookingPage from "./pages/VenueBookingPage";
import UpdateEventPage from "./pages/UpdateEventPage";
import HomePage from "./pages/HomePage";

function App() {
    return (

            <div className="App">
                <Header />
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/event/:eventId" element={<EventDetailPage />} />
                    <Route path="/create-event" element={<CreateEventPage />} />
                    <Route path="/event/:eventId/update" element={<UpdateEventPage />} />
                    <Route path="/venue-booking" element={<VenueBookingPage />} />
                    <Route path="/event/:eventId/budget-management" element={<BudgetManagementPage />} />

                </Routes>
            </div>

    );
}

export default App;