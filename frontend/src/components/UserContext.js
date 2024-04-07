import React, { createContext, useState, useContext, useEffect } from 'react';
import Cookies from 'js-cookie';

const UserContext = createContext();

export const UserProvider = ({ children }) => {
    const [loggedInUser, setLoggedInUser] = useState(null);
    const [error, setError] = useState(null);

    // Function to handle login
    const handleLogin = (user) => {
        setLoggedInUser(user);
        Cookies.set('loggedInUser', JSON.stringify(user), { expires: 7 }); // Set cookie expiration time
    };

    // Function to handle logout
    const handleLogout = () => {
        setLoggedInUser(null);
        Cookies.remove('loggedInUser');
    };

    // Check if there is a logged-in user in cookies on app load
    useEffect(() => {
        const storedUser = Cookies.get('loggedInUser');
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
