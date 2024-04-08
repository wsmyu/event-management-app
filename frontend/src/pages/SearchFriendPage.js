import React, { useState } from 'react';
import { useUser } from '../components/UserContext';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import ListGroup from 'react-bootstrap/ListGroup';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Modal from 'react-bootstrap/Modal';
import Toast from 'react-bootstrap/Toast';

const SearchFriendPage = () => {
  const { loggedInUser } = useUser();
  const [searchUsername, setSearchUsername] = useState('');
  const [searchResult, setSearchResult] = useState(null);
  const [selectedUser, setSelectedUser] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [showSuccessToast, setShowSuccessToast] = useState(false);
  const [showErrorToast, setShowErrorToast] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const handleInputChange = (e) => {
    setSearchUsername(e.target.value);
  };

  const handleSearch = () => {
    fetch(`http://localhost:8080/api/users/search?username=${searchUsername}`)
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          console.error('Search failed:', response.statusText);
          throw new Error('Search failed');
        }
      })
      .then(result => {
        setSearchResult(result);
      })
      .catch(error => {
        console.error('Error during search:', error.message);
      });
  };

  const handleAddFriend = (userId) => {
    console.log(`Adding user with ID ${userId} to friends.`);

    fetch(`http://localhost:8080/api/users/${loggedInUser.userId}/friends/add`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${loggedInUser.token}`,
      },
      body: JSON.stringify({
        friendUserId: userId,
      }),
    })
      .then(response => {
        if (response.ok) {
          setShowSuccessToast(true);
          // You might want to update the UI or state to reflect the friend addition
        } else {
          response.text().then(text => {
            setErrorMessage(text);
            setShowErrorToast(true);
          });
          console.error('Add friend failed:', response.statusText);
          throw new Error('Add friend failed');
        }
      })
      .catch(error => {
        setShowErrorToast(true);
        console.error('Error during add friend:', error.message);
      });
  };

  const handleShowUserInfo = (user) => {
    setSelectedUser(user);
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  return (
    <Container>
      <h1>Search for friends</h1>
      <Row className="mb-3">
        <Col md={6}>
          <Form>
            <Form.Control type="text" placeholder="Search by Username" value={searchUsername} onChange={handleInputChange} />
          </Form>
        </Col>
        <Col>
          <Button variant="primary" onClick={handleSearch}>Search</Button>
        </Col>
      </Row>

      {searchResult && (
        <div style={{ width: 'fit-content' }}>
          <ListGroup style={{ width: 'fit-content', border: 'none' }}>
            {searchResult.map(user => (
              <ListGroup.Item key={user.userId} style={{ display: 'flex', alignItems: 'center', border: 'none', cursor: 'pointer' }}>
                <Button variant="success" onClick={() => handleAddFriend(user.userId)} style={{ marginRight: '10px' }}>+</Button>
                <a href="#" onClick={() => handleShowUserInfo(user)} style={{ textDecoration: 'underline' }}>{user.username}</a>
              </ListGroup.Item>
            ))}
          </ListGroup>
        </div>
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
            </>
          )}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>Close</Button>
        </Modal.Footer>
      </Modal>

      {/* Toast for success */}
      <Toast
        onClose={() => setShowSuccessToast(false)}
        show={showSuccessToast}
        delay={3000}
        autohide
        style={{
          position: 'fixed',
          top: 20,
          right: 20,
          backgroundColor: '#4CAF50', // Green color
        }}
      >
        <Toast.Header closeButton={false}>
          <strong className="me-auto">Success</strong>
        </Toast.Header>
        <Toast.Body>Friend request sent successfully!</Toast.Body>
      </Toast>


      {/* Toast for error */}
      <Toast
        onClose={() => setShowErrorToast(false)}
        show={showErrorToast}
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
          <strong className="me-auto">Error</strong>
        </Toast.Header>
        <Toast.Body>{errorMessage}</Toast.Body>
      </Toast>
    </Container>
  );
};

export default SearchFriendPage;
