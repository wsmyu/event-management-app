import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useUser } from './UserContext'; // Adjust the import path according to your structure

const ProtectedRoute = ({ children }) => {
  const { loggedInUser } = useUser(); // Use your context to get the logged-in user
  const location = useLocation();

  if (!loggedInUser) {
    // Redirect to login but remember the attempted location
    return <Navigate to="/user/login" state={{ from: location }} replace />;
  }

  return children;
};

export default ProtectedRoute;
