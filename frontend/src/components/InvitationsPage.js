import React, { useState, useEffect, useCallback } from 'react';
import { useUser } from '../components/UserContext';
import { Link } from 'react-router-dom';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Alert from 'react-bootstrap/Alert';
import Container from 'react-bootstrap/Container';

const InvitationsPage = () => {
  const { loggedInUser } = useUser();
  const [invitations, setInvitations] = useState([]);

  const fetchEventDetails = async (eventId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/events/${eventId}`, {
        headers: {
          'Authorization': `Bearer ${loggedInUser.token}`,
        },
      });
      if (!response.ok) {
        throw new Error(`Failed to fetch event details for event ID: ${eventId}`);
      }
      const data = await response.json();
      return data.eventName; // Assuming your event object has an eventName property
    } catch (error) {
      console.error('Error fetching event details:', error);
      return 'Unknown Event'; // Fallback event name
    }
  };

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
      let data = await response.json();

      // Fetch event names for each invitation
      for (let invitation of data) {
        invitation.eventName = await fetchEventDetails(invitation.eventId);
      }

      setInvitations(data);
    } catch (error) {
      console.error('Error fetching invitations:', error);
    }
  }, [loggedInUser]);

  useEffect(() => {
    fetchInvitations();
  }, [fetchInvitations]);

  const handleResponse = async (invitationId, status) => {
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
      fetchInvitations(); // Refresh invitations list
    } catch (error) {
      console.error('Error updating invitation status:', error);
    }
  };

    return (
      <Container className="mt-5">
        <h2 className="mb-4">My Invitations</h2>
        {invitations.length > 0 ? (
          invitations.map((invitation) => (
            <Card key={invitation.guestId} className="mb-3">
              <Card.Body>
                <Card.Title>
                  <Link to={`/event/${invitation.eventId}`} className="text-decoration-none">
                    {invitation.eventName}
                  </Link>
                </Card.Title>
                <Card.Text>
                  Status: <strong>{invitation.status}</strong>
                </Card.Text>
                <Button variant="success" className="me-2" onClick={() => handleResponse(invitation.guestId, 'accepted')}>
                  Accept
                </Button>
                <Button variant="danger" onClick={() => handleResponse(invitation.guestId, 'rejected')}>
                  Reject
                </Button>
              </Card.Body>
            </Card>
          ))
        ) : (
          <Alert variant="info">No invitations found.</Alert>
        )}
      </Container>
    );
  };

export default InvitationsPage;
