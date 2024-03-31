import React, { createContext, useState, useContext, useEffect } from 'react';

const UserContext = createContext();

export const UserProvider = ({ children }) => {
    const [loggedInUser, setLoggedInUser] = useState(null);
    const [error, setError] = useState(null);

    // Function to handle login
    const handleLogin = (user) => {
        setLoggedInUser(user);
        localStorage.setItem('loggedInUser', JSON.stringify(user));
    };

    // Function to handle logout
    const handleLogout = () => {
        setLoggedInUser(null);
        localStorage.removeItem('loggedInUser');
    };

    // Check if there is a logged-in user in local storage on app load
    useEffect(() => {
        const storedUser = localStorage.getItem('loggedInUser');
        if (storedUser) {
            setLoggedInUser(JSON.parse(storedUser));
        }
    }, []);

    return (
        <UserContext.Provider value={{ loggedInUser, error, handleLogin, handleLogout, setError }}>
            {children}
        </UserContext.Provider>
    );
};

export const useUser = () => useContext(UserContext);
