import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useUser } from '../components/UserContext';
import { Button, Form, ListGroup, Container, Row, Col, Card, InputGroup, FormControl } from 'react-bootstrap';

const GuestManagementPage = () => {
  const { eventId } = useParams();
  const { loggedInUser } = useUser();
  const [searchUsername, setSearchUsername] = useState('');
  const [searchResult, setSearchResult] = useState([]);
  const [guestList, setGuestList] = useState([]);

  const handleSearchUser = () => {
    fetch(`http://localhost:8080/api/users/search?username=${searchUsername}`, {
      headers: { 'Authorization': `Bearer ${loggedInUser.token}` },
    })
      .then((response) => response.json())
      .then(setSearchResult)
      .catch((error) => console.error('Error searching user:', error));
  };

  const handleSendInvitation = (userId) => {
    fetch(`http://localhost:8080/api/guests/${loggedInUser.userId}/manage`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${loggedInUser.token}` },
      body: JSON.stringify({ userId: userId, eventId: Number(eventId), status: 'pending' }),
    })
      .then((response) => {
        if (!response.ok) throw new Error('Failed to send invitation');
        return response.json();
      })
      .then(() => fetchGuestList())
      .catch((error) => console.error('Error sending invitation:', error));
  };

  const fetchUserDetails = async (userId) => {
    const response = await fetch(`http://localhost:8080/api/users/${userId}`, {
      headers: { 'Authorization': `Bearer ${loggedInUser.token}` },
    });
    if (!response.ok) throw new Error('Failed to fetch user details');
    const userDetails = await response.json();
    return userDetails.username;
  };

  const fetchGuestList = async () => {
    const response = await fetch(`http://localhost:8080/api/guests/${eventId}`, {
      headers: { 'Authorization': `Bearer ${loggedInUser.token}` },
    });
    if (!response.ok) throw new Error('Failed to fetch guest list');
    const guests = await response.json();

    // Ensuring we retain the logic to fetch usernames for each guest
    const guestsWithUsernames = await Promise.all(
      guests.map(async (guest) => {
        const username = await fetchUserDetails(guest.userId);
        return { ...guest, username };
      })
    );

    setGuestList(guestsWithUsernames);
  };

  useEffect(() => {
    if (loggedInUser) {
      fetchGuestList();
    }
  }, [loggedInUser, eventId]);

  return (
    <Container fluid="md" className="my-4">
      <Row className="justify-content-md-center">
        <Col md={8}>
          <h2>Guest Management for Event {eventId}</h2>
          <InputGroup className="mb-3">
            <FormControl
              placeholder="Search by Username"
              aria-label="Search by Username"
              value={searchUsername}
              onChange={(e) => setSearchUsername(e.target.value)}
            />
            <Button variant="outline-secondary" id="button-addon2" onClick={handleSearchUser}>
              Search
            </Button>
          </InputGroup>

          <h3>User Search Result</h3>
          {searchResult && searchResult.length > 0 ? (
            <ListGroup>
              {searchResult.map((user) => (
                <ListGroup.Item key={user.userId} className="d-flex justify-content-between align-items-center">
                  {user.username}
                  <Button variant="primary" onClick={() => handleSendInvitation(user.userId)}>Invite</Button>
                </ListGroup.Item>
              ))}
            </ListGroup>
          ) : (
            <p>No results found.</p>
          )}

          <h3 className="mt-4">Invited Guests</h3>
          {guestList && guestList.length > 0 ? (
            <ListGroup>
              {guestList.map((guest) => (
                <ListGroup.Item key={guest.guestId}>
                  {guest.username} - {guest.status}
                </ListGroup.Item>
              ))}
            </ListGroup>
          ) : (
            <p>No guests found.</p>
          )}
        </Col>
      </Row>
    </Container>
  );
};

export default GuestManagementPage;
