import React, { useState } from 'react';

const CreateUser = () => {
  const [user, setUser] = useState({
    userId: '',
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    email: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUser((prevUser) => ({
      ...prevUser,
      [name]: value,
    }));
  };

  const handleCreateUser = () => {
    console.log('Creating user:', user);

    // Add the fetch request to create the user in the backend.
    // Example using fetch:
    fetch('http://localhost:8080/api/users/create', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(user),
    })
      .then(response => response.json())
      .then(data => {
        console.log('User created successfully:', data);
        // Handle any additional logic after user creation.
      })
      .catch(error => {
        console.error('Error creating user:', error.message);
        // Handle errors.
      });
  };

  return (
    <div>
      <h1>Create User</h1>
      <label>Username:</label>
      <input type="text" name="username" value={user.username} onChange={handleInputChange} />
      <label>Password:</label>
      <input type="password" name="password" value={user.password} onChange={handleInputChange} />
      <label>First Name:</label>
      <input type="text" name="firstName" value={user.firstName} onChange={handleInputChange} />
      <label>Last Name:</label>
      <input type="text" name="lastName" value={user.lastName} onChange={handleInputChange} />
      <label>Email:</label>
      <input type="text" name="email" value={user.email} onChange={handleInputChange} />

      <button onClick={handleCreateUser}>Create User</button>
    </div>
  );
};

export default CreateUser;
