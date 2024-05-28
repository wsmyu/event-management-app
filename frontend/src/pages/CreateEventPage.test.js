import React from 'react';
import {render, screen, fireEvent, waitFor, getByTestId} from '@testing-library/react';
import CreateEventPage from './CreateEventPage';
import '@testing-library/jest-dom/extend-expect';
import {UserProvider, useUser} from '../components/UserContext';
import {MemoryRouter, Router} from "react-router-dom";


//Mock the useUser hook
jest.mock('../components/UserContext', () => ({
    useUser: jest.fn(), // Mock the useUser hook
}));

// Mock window.scrollTo
window.scrollTo = jest.fn();
// Mock the fetch function
global.fetch = jest.fn();
// Mock window.alert
global.alert = jest.fn();



test('renders CreateEventPage',()=>{
    // Mock the useUser hook to return a user object
    useUser.mockReturnValue({ loggedInUser: { userId: 1, username: 'testuser' } });
    render(<CreateEventPage />);

    // Check that the form is rendered
    expect(screen.getByText('Create Event', { selector: 'h1' })).toBeInTheDocument();
    expect(screen.getByLabelText('Event Name')).toBeInTheDocument();
    expect(screen.getByLabelText('Event Type')).toBeInTheDocument();
    expect(screen.getByLabelText('Event Date')).toBeInTheDocument();
    expect(screen.getByLabelText('Event Start Time')).toBeInTheDocument();
    expect(screen.getByLabelText('Event End Time')).toBeInTheDocument();
    expect(screen.getByLabelText('Event Description')).toBeInTheDocument();

})

test('displays error message when required fields are empty', async () => {
    // Mock the useUser hook to return a user object
    useUser.mockReturnValue({ loggedInUser: { userId: 1, username: 'testuser' } });
    render(<CreateEventPage />);

    // Submit the form without filling in required fields
    fireEvent.click(screen.getByTestId('create-event-button'));

    // Check for error messages
    await waitFor(() => {
        expect(screen.getByText('Event Name is required')).toBeInTheDocument();
        expect(screen.getByText('Event Type is required')).toBeInTheDocument();
        expect(screen.getByText('Event Description is required')).toBeInTheDocument();
        expect(screen.getByText('Event Start Time is required')).toBeInTheDocument();
        expect(screen.getByText('Event End Time is required')).toBeInTheDocument();
        expect(screen.getByText('Event Date is required')).toBeInTheDocument();

    });
});



test('prompts login if user is not logged in', () => {
    // Mock the useUser hook to return null for loggedInUser
    useUser.mockReturnValue({ loggedInUser: null });

    render(<CreateEventPage />);

    // Check if the login prompt is displayed
    expect(screen.getByText('Please login first to create an event.')).toBeInTheDocument();
});