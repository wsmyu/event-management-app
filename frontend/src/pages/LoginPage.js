import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';
import { useUser } from '../components/UserContext';

const LoginPage = () => {
    const { handleLogin, setError } = useUser();
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

    const handleLoginRequest = () => {
        fetch('http://localhost:8080/api/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData),
        })
            .then(response => {
                if (response.ok) {
                    return response.text();
                } else {
                    throw new Error('Login failed');
                }
            })
            .then(token => {
                const decodedToken = jwtDecode(token);
                const userId = decodedToken.userId;

                fetch(`http://localhost:8080/api/users/${userId}`, {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                })
                    .then(response => {
                        if (response.ok) {
                            return response.json();
                        } else {
                            throw new Error('Failed to fetch user information.');
                        }
                    })
                    .then(user => {
                        handleLogin(user);
                        localStorage.setItem('token', token);
                        navigate('/');
                    })
                    .catch(error => {
                        setError('Failed to fetch user information.');
                    });
            })
            .catch(error => {
                setError('Invalid username or password.');
            });
    };

    return (
        <div>
            <h1>Login</h1>
            <label>Username:</label>
            <input type="text" name="username" value={loginData.username} onChange={handleInputChange} />
            <label>Password:</label>
            <input type="password" name="password" value={loginData.password} onChange={handleInputChange} />
            <button onClick={handleLoginRequest}>Login</button>
        </div>
    );
};

export default LoginPage;
