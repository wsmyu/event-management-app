import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const LoginPage = ({ onLogin }) => {
  const navigate = useNavigate();

  const [loginData, setLoginData] = useState({
    username: '',
    password: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setLoginData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleLogin = () => {
    // Call the backend API to perform login
    fetch('http://localhost:8080/api/users/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(loginData),
    })
      .then(response => {
        if (response.ok) {
          console.log('Login successful!');
          // Assuming the response contains user information, you can parse it
          return response.json();
        } else {
          console.error('Login failed:', response.statusText);
          throw new Error('Login failed');
        }
      })
      .then(user => {
        // Notify the parent component of the logged-in user
        onLogin(user);
        // Redirect to the home page
        navigate('/');
      })
      .catch(error => {
        console.error('Error during login:', error.message);
      });
  };

  return (
    <div>
      <h1>Login</h1>
      <label>Username:</label>
      <input type="text" name="username" value={loginData.username} onChange={handleInputChange} />
      <label>Password:</label>
      <input type="password" name="password" value={loginData.password} onChange={handleInputChange} />

      <button onClick={handleLogin}>Login</button>
    </div>
  );
};

export default LoginPage;
