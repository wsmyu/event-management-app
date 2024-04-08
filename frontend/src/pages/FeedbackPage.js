import React, { useState } from 'react';
import { useUser } from '../components/UserContext';
import { Toast } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

const FeedbackPage = () => {
  const { loggedInUser } = useUser();
  const navigate = useNavigate();
  const [feedback, setFeedback] = useState({
    userId: loggedInUser.userId,
    message: '',
    rating: 0,
  });
  const [showSuccessToast, setShowSuccessToast] = useState(false);
  const [showErrorToast, setShowErrorToast] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [validated, setValidated] = useState(false);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFeedback({ ...feedback, [name]: value });
  };

  const handleRatingChange = (value) => {
    setFeedback({ ...feedback, rating: parseInt(value, 10) });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const form = e.currentTarget;
    if (form.checkValidity() === false) {
      e.stopPropagation();
    }
    setValidated(true);

    if (form.checkValidity()) {
      fetch('http://localhost:8080/api/feedback/submit', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${loggedInUser.token}`,
        },
        body: JSON.stringify(feedback),
      })
        .then(response => {
          if (response.ok) {
            console.log('Feedback submitted successfully.');
            setShowSuccessToast(true);
            setFeedback({ ...feedback, message: '', rating: 0 });
            setTimeout(() => {
              navigate('/');
            }, 2000);
          } else {
            response.text().then(text => {
              setErrorMessage(text);
              setShowErrorToast(true);
            });
            console.error('Feedback submission failed:', response.statusText);
            throw new Error('Feedback submission failed');
          }
        })
        .catch(error => {
          console.error('Error submitting feedback:', error.message);
          alert('Failed to submit feedback. Please try again later.');
        });
    }
  };

  return (
    <div className="container mt-4">
      <div className="card" style={{ maxWidth: '500px', margin: '0 auto' }}>
        <div className="card-body">
          <h1 className="text-center mb-4">We need your feedback!</h1>
          <Form noValidate validated={validated} onSubmit={handleSubmit}>
            <Form.Group>
              <Form.Label>Rating:</Form.Label>
              <div className="d-flex flex-wrap">
                {[1, 2, 3, 4, 5].map((value) => (
                  <Form.Check
                    required
                    key={value}
                    type="radio"
                    id={`rating${value}`}
                    name="rating"
                    value={value}
                    checked={feedback.rating === value}
                    onChange={() => handleRatingChange(value)}
                    className="me-2"
                    isInvalid={validated && feedback.rating === 0}
                    label={value}
                  />
                ))}
                <Form.Control.Feedback type="invalid">Please select a rating.</Form.Control.Feedback>
              </div>
            </Form.Group>
            <Form.Group>
              <Form.Label>Message:</Form.Label>
              <Form.Control
                as="textarea"
                name="message"
                value={feedback.message}
                onChange={handleInputChange}
                required
                isInvalid={validated && feedback.message === ''}
                className="form-control-lg"
              />
              <Form.Control.Feedback type="invalid">Please provide a message.</Form.Control.Feedback>
            </Form.Group>
            <div className="text-center mt-3">
              <Button type="submit" className="btn btn-primary">Submit Feedback</Button>
            </div>
          </Form>
        </div>
      </div>

      {/* Success Toast */}
      <Toast
        onClose={() => setShowSuccessToast(false)}
        show={showSuccessToast}
        delay={2000} // Display for 2 seconds
        autohide
        bg="success"
        style={{
          position: 'fixed',
          top: 20,
          right: 20,
        }}
      >
        <Toast.Body>Feedback submitted successfully!</Toast.Body>
      </Toast>

      {/* Error Toast */}
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
    </div>
  );
};

export default FeedbackPage;
