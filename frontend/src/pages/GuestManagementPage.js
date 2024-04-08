import React, { useState,useEffect} from 'react';
import { useParams } from 'react-router-dom';
import { useUser } from '../components/UserContext';

const GuestManagementPage = () => {
  const { eventId } = useParams();
  const { loggedInUser } = useUser();
  const [searchUsername, setSearchUsername] = useState('');
  const [searchResult, setSearchResult] = useState([]);
  const [guestList, setGuestList] = useState([]);

  const handleSearchUser = () => {
    fetch(`http://localhost:8080/api/users/search?username=${searchUsername}`, {
      headers: {
        'Authorization': `Bearer ${loggedInUser.token}`,
      },
    })
      .then(response => response.json())
      .then(data => {
        setSearchResult(data);
      })
      .catch(error => console.error('Error searching user:', error));
  };

  const handleSendInvitation = (userId) => {
    // Adjust the request payload to match the API expectation
    fetch(`http://localhost:8080/api/guests/manage`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${loggedInUser.token}`,
      },
      body: JSON.stringify({
        userId: userId,
        eventId: Number(eventId),
        status: 'pending',
      }),
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Failed to send invitation');
      }
      return response.json();
    })
    .then(() => {
      // Refresh guest list or show success message
      fetchGuestList();
    })
    .catch(error => console.error('Error sending invitation:', error));
  };
const fetchUserDetails = async (userId) => {
  try {
    const response = await fetch(`http://localhost:8080/api/users/${userId}`, {
      headers: {
        'Authorization': `Bearer ${loggedInUser.token}`,
      },
    });
    if (!response.ok) {
      throw new Error('Failed to fetch user details');
    }
    const userDetails = await response.json();
    return userDetails.username;
  } catch (error) {
    console.error('Error fetching user details:', error);
    return 'Unknown'; // Fallback username
  }
};

 const fetchGuestList = async () => {
   try {
     const response = await fetch(`http://localhost:8080/api/guests/${eventId}`, {
       headers: {
         'Authorization': `Bearer ${loggedInUser.token}`,
       },
     });
     if (!response.ok) {
       throw new Error('Failed to fetch guest list');
     }
     const guests = await response.json();

     // Fetch usernames for each guest
     const guestsWithUsernames = await Promise.all(guests.map(async (guest) => {
       const username = await fetchUserDetails(guest.userId);
       return { ...guest, username }; // Append username to the guest object
     }));

     setGuestList(guestsWithUsernames);
   } catch (error) {
     console.error('Error fetching guest list:', error);
   }
 };


useEffect(() => {
  if (loggedInUser) {
    fetchGuestList();
  }
}, [loggedInUser]); // Add loggedInUser as a dependency

  return (
    <div>
      <h2>Guest Management for Event {eventId}</h2>
      <div>
        <label>Search by Username:</label>
        <input type="text" value={searchUsername} onChange={(e) => setSearchUsername(e.target.value)} />
        <button onClick={handleSearchUser}>Search</button>
      </div>
      <div>
        <h3>Search Results</h3>
        <ul>
          {searchResult.map(user => (
            <li key={user.userId}>
              {user.username}
              <button onClick={() => handleSendInvitation(user.userId)}>Invite as Guest</button>
            </li>
          ))}
        </ul>
      </div>
<div>
  <h3>Invited Guests</h3>
<ul>
  {guestList.map(guest => (
    <li key={guest.guestId}>{guest.username} - {guest.status}</li>
  ))}
</ul>
</div>
    </div>
  );
};

export default GuestManagementPage;
