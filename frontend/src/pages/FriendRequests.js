import React, { useState, useEffect } from 'react';
import { useUser } from '../components/UserContext';
import { Button, Modal, ListGroup, Container, Row, Col, Toast } from 'react-bootstrap';

const FriendRequests = () => {
  const { loggedInUser } = useUser();
  const [friendRequests, setFriendRequests] = useState([]);
  const [selectedSender, setSelectedSender] = useState(null);
  const [senderInfo, setSenderInfo] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [showSuccessToast, setShowSuccessToast] = useState(false);
  const [showRejectToast, setShowRejectToast] = useState(false);

  useEffect(() => {
    fetchFriendRequests();
  }, [loggedInUser]);

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
      .then(async requests => {
        console.log('Friend requests:', requests); // Log the response data

        // Fetch user information for each sender
        const requestsWithSenderInfo = await Promise.all(requests.map(async request => {
          const userInfoResponse = await fetch(`http://localhost:8080/api/users/${request.userId}`, {
            headers: {
              'Authorization': `Bearer ${loggedInUser.token}`
            }
          });
          if (userInfoResponse.ok) {
            const userInfo = await userInfoResponse.json();
            request.senderInfo = userInfo;
          } else {
            console.error('Failed to fetch user information for request:', request.id);
          }
          return request;
        }));

        setFriendRequests(requestsWithSenderInfo);
      })
      .catch(error => {
        console.error('Error fetching friend requests:', error.message);
      });
  };

  const handleAcceptRequest = (friendRequestId, senderUsername) => {
    if (!loggedInUser) {
      console.error('User is not logged in.');
      return;
    }

    fetch(`http://localhost:8080/api/users/${loggedInUser.userId}/friends/${friendRequestId}/accept`, {
      method: 'PUT',
      headers: {
        'Authorization': `Bearer ${loggedInUser.token}`
      }
    })
      .then(response => {
        if (response.ok) {
          fetchFriendRequests(); // Refresh friend requests after accepting
          setShowSuccessToast(true); // Show success toast
        } else {
          console.error('Failed to accept friend request:', response.statusText);
        }
      })
      .catch(error => {
        console.error('Error accepting friend request:', error.message);
      });
  };

  const handleDeleteRequest = (friendRequestId, senderUsername) => {
    if (!loggedInUser) {
      console.error('User is not logged in.');
      return;
    }

    fetch(`http://localhost:8080/api/users/${loggedInUser.userId}/friends/${friendRequestId}/delete`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${loggedInUser.token}`
      }
    })
      .then(response => {
        if (response.ok) {
          fetchFriendRequests(); // Refresh friend requests after deleting
          setShowRejectToast(true);
        } else {
          console.error('Failed to delete friend request:', response.statusText);
        }
      })
      .catch(error => {
        console.error('Error deleting friend request:', error.message);
      });
  };

  const handleUsernameClick = async (senderId) => {
    setSelectedSender(senderId);
    const userInfoResponse = await fetch(`http://localhost:8080/api/users/${senderId}`, {
      headers: {
        'Authorization': `Bearer ${loggedInUser.token}`
      }
    });
    if (userInfoResponse.ok) {
      const userInfo = await userInfoResponse.json();
      setSenderInfo(userInfo);
      setShowModal(true);
    } else {
      console.error('Failed to fetch sender information for user ID:', senderId);
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  if (!loggedInUser) {
      return (
          <div className="container mt-4">
            <p className="text-center">Please login to access your friend request.</p>
          </div>
      );
  }
  return (
    <Container>
      <Row className="justify-content-center">
        <Col xs={12} md={8}>
          <h2>Friend Requests</h2>
          {friendRequests.length === 0 ? (
            <p>No friend requests found</p>
          ) : (
            <ListGroup>
              {friendRequests.map(request => (
                <ListGroup.Item key={request.id}>
                  <Row>
                    <Col>
                      <span>
                        <a href="#" className="friend-request-username" onClick={() => handleUsernameClick(request.senderInfo.userId)} >
                          {request.senderInfo.username}
                        </a> sent you a friend request
                      </span>
                    </Col>
                    <Col xs="auto">
                      <div
                          className="d-flex gap-2">
                        <Button variant="outline-success"
                                onClick={() => handleAcceptRequest(request.friendId, request.senderInfo.username)}>Accept</Button>
                        <Button variant="outline-danger"
                                onClick={() => handleDeleteRequest(request.friendId, request.senderInfo.username)}>Delete</Button>
                      </div>
                    </Col>
                  </Row>
                </ListGroup.Item>
              ))}
            </ListGroup>
          )}
          {/* show user information */}
          <Modal show={showModal} onHide={handleCloseModal}>
            <Modal.Header closeButton>
              <Modal.Title>User Information</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <p>User ID: {senderInfo && senderInfo.userId}</p>
              <p>Username: {senderInfo && senderInfo.username}</p>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={handleCloseModal}>
                Close
              </Button>
            </Modal.Footer>
          </Modal>
        </Col>
      </Row>

      {/* Friend request accepted toast */}
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
        <Toast.Body>Friend request accepted.</Toast.Body>
      </Toast>

      {/* Friend request rejected toast */}
      <Toast
        onClose={() => setShowRejectToast(false)}
        show={showRejectToast}
        delay={3000}
        autohide
        bg="danger"
        style={{
          position: 'fixed',
          top: 20,
          right: 20,
        }}
      >
        <Toast.Header closeButton={false}>
          <strong className="me-auto">Rejection</strong>
        </Toast.Header>
        <Toast.Body>Friend request has been rejected.</Toast.Body>
      </Toast>
    </Container>
  );
};

export default FriendRequests;
