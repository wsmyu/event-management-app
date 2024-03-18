import React, { useState } from 'react';
import {Route, Routes } from 'react-router-dom';
import CreateUser from "./pages/CreateUserPage";
import LoginPage from "./pages/LoginPage";
import EventDetailPage from "./pages/EventDetailPage";
import CreateEventPage from "./pages/CreateEventPage";
import BudgetManagementPage from "./pages/BudgetManagementPage";
import Header from "./components/Header";
import "./App.css";
import VenueBookingPage from "./pages/VenueBookingPage";
import UpdateEventPage from "./pages/UpdateEventPage";
import HomePage from "./pages/HomePage";
import FriendsPage from "./pages/FriendsPage";
import SuccessPage from "./pages/SuccessPage";

function App() {
    const [loggedInUser, setLoggedInUser] = useState(null);

      const handleLogin = (user) => {
        setLoggedInUser(user);
      };

      const handleLogout = () => {
        setLoggedInUser(null);
      };

    return (

            <div className="App">
                <Header loggedInUser={loggedInUser} onLogout={handleLogout} />
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/user/create" element={<CreateUser />} />
                    <Route path="/user/login" element={<LoginPage onLogin={handleLogin} />} />
                    <Route path="/event/:eventId" element={<EventDetailPage />} />
                    <Route path="/create-event" element={<CreateEventPage />} />
                    <Route path="/event/:eventId/update" element={<UpdateEventPage />} />
                    <Route path="/venue-booking/:eventId" element={<VenueBookingPage />} />
                    <Route path="/event/:eventId/budget-management" element={<BudgetManagementPage />} />
                    <Route path="/user/:id/friends" element={<FriendsPage loggedInUser={loggedInUser} />} />
                    <Route path="/create-event-successful" element={<SuccessPage />} />
                    <Route path="/venue-booking-successful" element={<SuccessPage />} />
                </Routes>
            </div>

    );
}

export default App;