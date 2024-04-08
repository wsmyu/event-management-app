import React, { useState, useEffect, useCallback } from 'react';
import { useUser } from '../components/UserContext';

const InvitationsPage = () => {
  const { loggedInUser } = useUser();
  const [invitations, setInvitations] = useState([]);

  const fetchInvitations = useCallback(async () => {
    if (!loggedInUser || !loggedInUser.userId) {
      console.log("Waiting for user login info...");
      return;
    }
    console.log("Fetching invitations for user: ", loggedInUser.userId);
    try {
      const response = await fetch(`http://localhost:8080/api/guests/user/${loggedInUser.userId}/invitations`, {
        headers: {
          'Authorization': `Bearer ${loggedInUser.token}`,
        },
      });
      if (!response.ok) {
        throw new Error('Failed to fetch invitations');
      }
      const data = await response.json();
      setInvitations(data);
    } catch (error) {
      console.error('Error fetching invitations:', error);
    }
  }, [loggedInUser]); // `loggedInUser` is a dependency here

  useEffect(() => {
    fetchInvitations();
  }, [fetchInvitations]);  // Ensuring fetchInvitations is called whenever loggedInUser changes

const handleResponse = async (invitationId, status) => {
//  if (!loggedInUser?.token) {
//    console.log("Awaiting user login information...");
//    // This is where you might inform the user or handle unauthenticated state
//    return;
//  }
  try {
    const response = await fetch(`http://localhost:8080/api/guests/${invitationId}/status`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${loggedInUser.token}`,
      },
      body: JSON.stringify({ status }),
    });
    if (!response.ok) {
      throw new Error('Failed to update invitation status');
    }
    fetchInvitations(); // Assuming this fetches and updates the invitations state
  } catch (error) {
    console.error('Error updating invitation status:', error);
  }
};

  return (
    <div>
      <h2>My Invitations</h2>
      {invitations.length > 0 ? invitations.map((invitation) => (
        <div key={invitation.guestId}>
          <p>Event ID: {invitation.eventId} - Status: {invitation.status}</p>
          <button onClick={() => handleResponse(invitation.guestId, 'accepted')}>Accept</button>
          <button onClick={() => handleResponse(invitation.guestId, 'rejected')}>Reject</button>
        </div>
      )) : <p>No invitations found.</p>}
    </div>
  );
};

export default InvitationsPage;
