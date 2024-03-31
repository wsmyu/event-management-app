import React, { useState, useEffect } from 'react';
import { useUser } from '../components/UserContext';

const FriendRequests = () => {
  const { loggedInUser } = useUser();
  const [friendRequests, setFriendRequests] = useState([]);

  useEffect(() => {
    fetchFriendRequests();
  }, []);

  const fetchFriendRequests = () => {
    if (!loggedInUser) {
      console.error('User is not logged in.');
      return;
    }

    fetch(`http://localhost:8080/api/users/${loggedInUser.userId}/friends/requests`, {
      headers: {
        'Authorization': `Bearer ${loggedInUser.token}`
      }
    })
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          console.error('Failed to fetch friend requests:', response.statusText);
          throw new Error('Failed to fetch friend requests');
        }
      })
      .then(requests => {
        console.log('Friend requests:', requests); // Log the response data
        setFriendRequests(requests);
      })
      .catch(error => {
        console.error('Error fetching friend requests:', error.message);
      });
  };


  const handleAcceptRequest = (friendRequestId) => {
    // Implement logic to accept friend request
    console.log('Accepting friend request with ID:', friendRequestId);
  };

  const handleDeleteRequest = (friendRequestId) => {
    // Implement logic to delete friend request
    console.log('Deleting friend request with ID:', friendRequestId);
  };

  return (
    <div>
      <h2>Friend Requests</h2>
      <ul>
        {friendRequests.map(request => (
          <li key={request.id}>
            <span>{request.senderName} sent you a friend request</span>
            <button onClick={() => handleAcceptRequest(request.id)}>Accept</button>
            <button onClick={() => handleDeleteRequest(request.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default FriendRequests;
