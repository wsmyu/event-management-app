import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';
import { useUser } from '../components/UserContext';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Toast from 'react-bootstrap/Toast';

const LoginPage = () => {
    const { handleLogin, setError } = useUser();
    const navigate = useNavigate();

    const [loginData, setLoginData] = useState({
        username: '',
        password: '',
    });
    const [validated, setValidated] = useState(false);
    const [showErrorToast, setShowErrorToast] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');
    const [showSuccessToast, setShowSuccessToast] = useState(false);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setLoginData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleLoginRequest = (e) => {
        e.preventDefault();
        const form = e.currentTarget;
        if (form.checkValidity() === false) {
            e.stopPropagation();
        }
        setValidated(true);

        if (form.checkValidity()) {
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
                            setShowSuccessToast(true);
                            setTimeout(() => {
                                navigate('/');
                            }, 2000);
                        })
                        .catch(error => {
                            setError('Failed to fetch user information.');
                        });
                })
                .catch(error => {
                    setErrorMessage('Invalid username or password.');
                    setShowErrorToast(true);
                });
        }
    };

    return (
        <div className="container d-flex justify-content-center align-items-center ">
            <div className="text-center">
            <h1 className="mb-3">Login</h1>
            <Form noValidate validated={validated} onSubmit={handleLoginRequest}>
                <Form.Group className="mb-3" controlId="validationCustomUsername">
                    <Form.Control
                        type="text"
                        placeholder="Username"
                        aria-describedby="inputGroupPrepend"
                        required
                        name="username"
                        value={loginData.username}
                        onChange={handleInputChange}
                    />
                    <Form.Control.Feedback type="invalid">
                        Please provide a username.
                    </Form.Control.Feedback>
                </Form.Group>
                <Form.Group className="mb-3" controlId="validationCustomPassword">
                    <Form.Control
                        type="password"
                        placeholder="Password"
                        required
                        name="password"
                        value={loginData.password}
                        onChange={handleInputChange}
                    />
                    <Form.Control.Feedback type="invalid">
                        Please provide a password.
                    </Form.Control.Feedback>
                </Form.Group>
                <button className="custom-button" type="submit">Login</button>

            </Form>
            </div>

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
                <Toast.Body>{errorMessage}</Toast.Body>
            </Toast>

            {/* Toast for success */}
            <Toast
                onClose={() => setShowSuccessToast(false)}
                show={showSuccessToast}
                delay={2000}
                autohide
                bg="success"
                style={{
                    position: 'fixed',
                    top: 20,
                    right: 20,
                }}
            >
                <Toast.Body>Login successful!</Toast.Body>
            </Toast>
        </div>
    );
};

export default LoginPage;
