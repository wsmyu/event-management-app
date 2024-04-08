import React, { useState, useEffect } from 'react';
import { useUser } from '../components/UserContext';
import ListGroup from 'react-bootstrap/ListGroup';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';

const FriendList = () => {
  const { loggedInUser } = useUser();
  const [friends, setFriends] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [showModal, setShowModal] = useState(false);

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
        console.log('Friends:', friends); // Log the response data

        // Fetch user information for each friend
        const friendsWithUserInfo = await Promise.all(friends.map(async friend => {
          // Determine which id to use as the key
          const key = friend.userId !== loggedInUser.userId ? friend.userId : friend.friendUserId;

          // Fetch user information for the friend
          const userInfoResponse = await fetch(`http://localhost:8080/api/users/${key}`, {
            headers: {
              'Authorization': `Bearer ${loggedInUser.token}`
            }
          });

          if (userInfoResponse.ok) {
            const userInfo = await userInfoResponse.json();
            friend.username = userInfo.username; // Add username to the friend object
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
          setSelectedUser(userInfo[0]); // Assuming the first user in the list is the desired user
          setShowModal(true);
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


  const handleCloseModal = () => {
    setShowModal(false);
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center' }}>
      <div>
        <h2>Friend List</h2>
        {friends.length === 0 ? (
          <p>No friends found</p>
        ) : (
          <ListGroup style={{ width: 'fit-content' }}>
            {friends.map(friend => (
              <ListGroup.Item
                key={friend.userId !== loggedInUser.userId ? friend.userId : friend.friendUserId}
                action
                onClick={() => handleShowUserInfo(friend)}
                style={{ textDecoration: 'underline', cursor: 'pointer', width: '200px' }}
              >
                {friend.username}
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
              </>
            )}
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>Close</Button>
          </Modal.Footer>
        </Modal>
      </div>
    </div>
  );
};

export default FriendList;
