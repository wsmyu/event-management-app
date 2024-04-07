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
import SearchFriendPage from "./pages/SearchFriendPage";
import FriendListPage from "./pages/FriendListPage";
import SearchResultPage from "./pages/SearchResultPage";
import FriendRequests from "./pages/FriendRequests";
import FeedbackPage from "./pages/FeedbackPage";
import Footer from "./components/Footer";


function App() {
    return (
        <UserProvider>
            <div className="App">
                <Header />
                <div className='page-content'>
                    <Routes>
                        <Route path="/" element={<HomePage />} />
                        <Route path="/register" element={<CreateUser />} />
                        <Route path="/login" element={<LoginPage />} />
                        <Route path="/event/:eventId" element={<EventDetailPage />} />
                        <Route path="/create-event" element={<CreateEventPage />} />
                        <Route path="/event/:eventId/update" element={<UpdateEventPage />} />
                        <Route path="/venue-booking/:eventId" element={<VenueBookingPage />} />
                        <Route path="/event/:eventId/budget-management" element={<BudgetManagementPage />} />
                        <Route path="/search" element={<SearchResultPage />} />
                        <Route path="/user/friends" element={<FriendListPage />} />
                        <Route path="/user/search-friend" element={<SearchFriendPage />} />
                        <Route path="/user/request" element={<FriendRequests />} />
                        <Route path="/feedback" element={<FeedbackPage />} />
                    </Routes>
                </div>
                <Footer />

            </div>
        </UserProvider>
    );
}

export default App;
