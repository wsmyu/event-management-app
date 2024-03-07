import React, { useState } from 'react';

const LoginPage = () => {
  const [credentials, setCredentials] = useState({
    username: '',
    password: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCredentials((prevCredentials) => ({
      ...prevCredentials,
      [name]: value,
    }));
  };

  const handleLogin = () => {
    console.log('Logging in with credentials:', credentials);

    // Add the fetch request to authenticate the user with the backend.
    // Example using fetch:
    fetch('http://localhost:8080/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Login failed. Please check your credentials.');
        }
        return response.json();
      })
      .then(data => {
        console.log('Login successful. User data:', data);
        // Redirect to the desired page or handle login success.
      })
      .catch(error => {
        console.error('Error during login:', error.message);
        // Handle login errors.
      });
  };

  return (
    <div>
      <h1>Login</h1>
      <label>Username:</label>
      <input type="text" name="username" value={credentials.username} onChange={handleInputChange} />
      <label>Password:</label>
      <input type="password" name="password" value={credentials.password} onChange={handleInputChange} />

      <button onClick={handleLogin}>Login</button>
    </div>
  );
};

export default LoginPage;
