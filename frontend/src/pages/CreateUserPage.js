import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Toast from 'react-bootstrap/Toast';

function CreateUser() {
  const [validated, setValidated] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [showSuccessToast, setShowSuccessToast] = useState(false);

  const handleSubmit = async (event) => {
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    }

    setValidated(true);

    if (form.checkValidity()) {
      event.preventDefault();
      const formData = new FormData(form);
      const userData = {};
      formData.forEach((value, key) => {
        userData[key] = value;
      });
      try {
        const response = await fetch('http://localhost:8080/api/users/create', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(userData),
        });
        if (!response.ok) {
          const errorMessage = await response.text();
          setErrorMessage(errorMessage);
        } else {
          console.log('User created successfully');
          setShowSuccessToast(true);
          setTimeout(() => {
            window.location.href = '/login';
          }, 2000);
        }
      } catch (error) {
        console.error('Error creating user:', error.message);
      }
    }
  };

  return (
    <div className="container mt-4 w-75">
      <div className="card">
        <div className="card-body">
          <h1 className="text-center mb-4">Create Account</h1>
          <Form noValidate validated={validated} onSubmit={handleSubmit}>
            <Row className="mb-3">
              <Form.Group as={Col} md="6" controlId="validationCustomUsername">
                <Form.Label>Username</Form.Label>
                <InputGroup hasValidation>
                  <Form.Control
                    type="text"
                    placeholder="Username"
                    aria-describedby="inputGroupPrepend"
                    required
                    name="username"
                  />
                  <Form.Control.Feedback type="invalid">
                    Please choose a username.
                  </Form.Control.Feedback>
                </InputGroup>
              </Form.Group>
              <Form.Group as={Col} md="6" controlId="validationCustomPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Password"
                  required
                  name="password"
                />
                <Form.Control.Feedback type="invalid">
                  Please provide a password.
                </Form.Control.Feedback>
              </Form.Group>
            </Row>
            <Row className="mb-3">
              <Form.Group as={Col} md="6" controlId="validationCustomFirstName">
                <Form.Label>First Name</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="First Name"
                  required
                  name="firstName"
                />
                <Form.Control.Feedback type="invalid">
                  Please provide a first name.
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group as={Col} md="6" controlId="validationCustomLastName">
                <Form.Label>Last Name</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Last Name"
                  required
                  name="lastName"
                />
                <Form.Control.Feedback type="invalid">
                  Please provide a last name.
                </Form.Control.Feedback>
              </Form.Group>
            </Row>
            <Form.Group className="mb-3" controlId="validationCustomEmail">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                placeholder="Email"
                required
                name="email"
              />
              <Form.Control.Feedback type="invalid">
                Please provide a valid email.
              </Form.Control.Feedback>
            </Form.Group>
            {errorMessage && (
              <div className="alert alert-danger" role="alert">
                {errorMessage}
              </div>
            )}
            <div className="text-center gap-2">
              <button type="submit" className="custom-button w-50">Register</button>
            </div>
          </Form>
          {/* Success toast */}
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
            <Toast.Body>Account created successfully! You can now log in with your credentials.</Toast.Body>
          </Toast>
        </div>
      </div>
    </div>
  );
}

export default CreateUser;
