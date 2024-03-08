import React,  { useState } from 'react';
import {Route, Routes } from 'react-router-dom';
import CreateUser from "./pages/CreateUserPage";
import LoginPage from "./pages/LoginPage";
import EventDetailPage from "./pages/EventDetailPage";
import CreateEventPage from "./pages/CreateEventPage";
import FriendsPage from "./pages/FriendsPage";
import Header from "./components/Header";
import "./App.css";
import VenueBookingPage from "./pages/VenueBookingPage";

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
                    <Route path="/user/create" element={<CreateUser />} />
                    <Route path="/user/login" element={<LoginPage onLogin={handleLogin} />} />
                    <Route path="/event/:id" element={<EventDetailPage />} />
                    <Route path="/create-event" element={<CreateEventPage />} />
                    <Route path="/venue-booking" element={<VenueBookingPage />} />
                    <Route path="/user/:id/friends" element={<FriendsPage loggedInUser={loggedInUser} />} />
                </Routes>
            </div>

    );
}

export default App;