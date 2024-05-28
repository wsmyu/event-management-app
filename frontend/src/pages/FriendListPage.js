import React, { useState, useEffect } from 'react';
import { useUser } from '../components/UserContext';
import { Link } from 'react-router-dom';
import { Button, Modal, ListGroup, Container, Toast } from 'react-bootstrap';

const FriendList = () => {
  const { loggedInUser } = useUser();
  const [friends, setFriends] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [userEvents, setUserEvents] = useState([]);
  const [showConfirmationModal, setShowConfirmationModal] = useState(false);
  const [friendToDelete, setFriendToDelete] = useState(null);
  const [showSuccessToast, setShowSuccessToast] = useState(false);

  useEffect(() => {
    fetchFriends();
  }, [loggedInUser]);

  const fetchFriends = () => {
    if (!loggedInUser) {
      console.error('User is not logged in.');
      return;
    }

    fetch(`http://localhost:8080/api/users/${loggedInUser.userId}/friends/all`, {
      headers: {
        'Authorization': `Bearer ${loggedInUser.token}`
      }
    })
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          console.error('Failed to fetch friends:', response.statusText);
          throw new Error('Failed to fetch friends');
        }
      })
      .then(async friends => {
        console.log('Friends:', friends);

        const friendsWithUserInfo = await Promise.all(friends.map(async friend => {
          const key = friend.userId !== loggedInUser.userId ? friend.userId : friend.friendUserId;

          const userInfoResponse = await fetch(`http://localhost:8080/api/users/${key}`, {
            headers: {
              'Authorization': `Bearer ${loggedInUser.token}`
            }
          });

          if (userInfoResponse.ok) {
            const userInfo = await userInfoResponse.json();
            friend.username = userInfo.username;
          } else {
            console.error('Failed to fetch user information for friend:', key);
          }
          return friend;
        }));

        setFriends(friendsWithUserInfo);
      })
      .catch(error => {
        console.error('Error fetching friends:', error.message);
      });
  };

  const handleShowUserInfo = async (friend) => {
    try {
      const response = await fetch(`http://localhost:8080/api/users/search?username=${friend.username}`, {
        headers: {
          'Authorization': `Bearer ${loggedInUser.token}`
        }
      });
      if (response.ok) {
        const userInfo = await response.json();
        if (userInfo.length > 0) {
          setSelectedUser(userInfo[0]);
          setShowModal(true);
          fetchAcceptedEventIds(userInfo[0].userId);
        } else {
          console.error('User not found with username:', friend.username);
        }
      } else {
        console.error('Failed to fetch user with username:', friend.username);
      }
    } catch (error) {
      console.error('Error fetching user information for friend:', error.message);
    }
  };

  const fetchAcceptedEventIds = async (userId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/guests/user/${userId}/accepted-event`, {
        headers: {
          'Authorization': `Bearer ${loggedInUser.token}`
        }
      });
      if (response.ok) {
        const acceptedEventIds = await response.json();
        fetchUserEvents(acceptedEventIds);
      } else {
        console.error('Failed to fetch accepted event IDs for user:', userId);
      }
    } catch (error) {
      console.error('Error fetching accepted event IDs for user:', userId, error);
    }
  };

  const fetchUserEvents = async (acceptedEventIds) => {
    const eventPromises = acceptedEventIds.map(async (eventId) => {
      const response = await fetch(`http://localhost:8080/api/events/${eventId}`, {
        headers: {
          'Authorization': `Bearer ${loggedInUser.token}`
        }
      });
      if (response.ok) {
        const event = await response.json();
        return event;
      } else {
        console.error('Failed to fetch event details for event ID:', eventId);
        return null;
      }
    });
    const events = await Promise.all(eventPromises);
    setUserEvents(events.filter(event => event !== null));
  };

  const handleDeleteFriend = async (friendId) => {
    setFriendToDelete(friendId);
    setShowConfirmationModal(true);
  };

  const confirmDeleteFriend = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/users/${loggedInUser.userId}/friends/${friendToDelete}/delete`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${loggedInUser.token}`
        }
      });
      if (response.ok) {
        // Remove the friend from the list
        setFriends(prevFriends => prevFriends.filter(friend => friend.friendId !== friendToDelete));
        setShowSuccessToast(true);
      } else {
        console.error('Failed to delete friend:', friendToDelete);
      }
      setShowConfirmationModal(false);
    } catch (error) {
      console.error('Error deleting friend:', error);
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  if (!loggedInUser) {
    return (
        <div className="container mt-4">
          <p className="text-center">Please login to access your friend list.</p>
        </div>
    );
   }

  return (
    <Container>
      <div>
        <h2>Friend List</h2>
        {friends.length === 0 ? (
          <p>No friends found</p>
        ) : (
          <ListGroup style={{ width: '500px' }}>
            {friends.map(friend => (
              <ListGroup.Item
                key={friend.userId !== loggedInUser.userId ? friend.userId : friend.friendUserId}
                style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}
              >
                <span>{friend.username}</span>
                <div>
                  <Button className="mx-3" variant="outline-primary" onClick={() => handleShowUserInfo(friend)}>Info</Button>
                  <Button variant="outline-danger" onClick={() => handleDeleteFriend(friend.friendId)}>Delete</Button>
                </div>
              </ListGroup.Item>
            ))}
          </ListGroup>
        )}

        <Modal show={showModal} onHide={handleCloseModal}>
          <Modal.Header closeButton>
            <Modal.Title>User Information</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            {selectedUser && (
              <>
                <p>User ID: {selectedUser.userId}</p>
                <p>Username: {selectedUser.username}</p>
                <p>Joined Events:</p>
                <ul>
                  {userEvents.map(event => (
                    <li key={event.eventId}>
                      <Link to={`/event/${event.eventId}`}>{event.eventName}</Link>
                    </li>
                  ))}
                </ul>
              </>
            )}
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>Close</Button>
          </Modal.Footer>
        </Modal>

        {/* Confirmation Modal */}
        <Modal show={showConfirmationModal} onHide={() => setShowConfirmationModal(false)}>
          <Modal.Header closeButton>
            <Modal.Title>Confirm Deletion</Modal.Title>
          </Modal.Header>
          <Modal.Body>Are you sure you want to delete this friend?</Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={() => setShowConfirmationModal(false)}>Cancel</Button>
            <Button variant="danger" onClick={confirmDeleteFriend}>Delete</Button>
          </Modal.Footer>
        </Modal>

        {/* Success Toast */}
        <Toast
          onClose={() => setShowSuccessToast(false)}
          show={showSuccessToast}
          delay={3000}
          autohide
          bg="success"
          style={{
            position: 'fixed',
            top: 20,
            right: 20,
          }}
        >
          <Toast.Header closeButton={false}>
            <strong className="me-auto">Success</strong>
          </Toast.Header>
          <Toast.Body>Friend deleted successfully.</Toast.Body>
        </Toast>
      </div>
    </Container>
  );
};

export default FriendList;
