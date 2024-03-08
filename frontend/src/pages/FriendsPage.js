import React, { useState } from 'react';

const FriendsPage = ({ loggedInUser }) => {
  const [searchUsername, setSearchUsername] = useState('');
  const [searchResult, setSearchResult] = useState(null);

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
        // Set the search result in state
        setSearchResult(result);
      })
      .catch(error => {
        console.error('Error during search:', error.message);
      });
  };

   const handleAddFriend = (userId) => {
      // Implement the logic to add the user with userId to the friend list
      console.log(`Adding user with ID ${userId} to friends.`);

      // Make an API call to addFriend endpoint
      fetch(`http://localhost:8080/api/users/${loggedInUser.userId}/friends/add`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${loggedInUser.token}`, // Include authentication token
        },
        body: JSON.stringify({
          user_id: loggedInUser.userId, // Assuming the backend expects user_id
          friend_user_id: userId,
        }),
      })
        .then(response => {
          if (response.ok) {
            console.log('Friend added successfully.');
            // You might want to update the UI or state to reflect the friend addition
          } else {
            console.error('Add friend failed:', response.statusText);
            throw new Error('Add friend failed');
          }
        })
        .catch(error => {
          console.error('Error during add friend:', error.message);
        });
    };

  return (
    <div>
      <h1>Friends Page</h1>
      <div>
        <label>Search by Username:</label>
        <input type="text" value={searchUsername} onChange={handleInputChange} />
        <button onClick={handleSearch}>Search</button>
      </div>

      {searchResult && (
        <div>
          <h2>Search Result</h2>
          <ul>
            {searchResult.map(user => (
              <li key={user.userId}>
                {user.username}
                <button onClick={() => handleAddFriend(user.userId)}>Add Friend</button>
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default FriendsPage;
