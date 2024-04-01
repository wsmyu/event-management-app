import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { UserProvider } from './components/UserContext';
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
import SearchResultPage from "./pages/SearchResultPage";
import FriendRequests from "./pages/FriendRequests";


function App() {
    return (
        <UserProvider>
            <div className="App">
                <Header />
                <div className='page-content'>
                    <Routes>
                        <Route path="/" element={<HomePage />} />
                        <Route path="/user/create" element={<CreateUser />} />
                        <Route path="/user/login" element={<LoginPage />} />
                        <Route path="/event/:eventId" element={<EventDetailPage />} />
                        <Route path="/create-event" element={<CreateEventPage />} />
                        <Route path="/event/:eventId/update" element={<UpdateEventPage />} />
                        <Route path="/venue-booking/:eventId" element={<VenueBookingPage />} />
                        <Route path="/event/:eventId/budget-management" element={<BudgetManagementPage />} />
                        <Route path="/user/:id/friends" element={<FriendsPage  />} />
                        <Route path="/search" element={<SearchResultPage />} />
                        <Route path="/user/:id/friends" element={<FriendsPage />} />
                        <Route path="/user/:id/request" element={<FriendRequests />} />

                    </Routes>
                </div>

            </div>
        </UserProvider>
    );
}

export default App;
