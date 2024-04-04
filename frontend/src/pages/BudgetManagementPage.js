import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import CustomToast from "../components/CustomToast";

const BudgetManagementPage = () => {
  const { eventId } = useParams();
  const navigate = useNavigate();

  const [budget, setBudget] = useState({
    venueCost: '',
    beverageCostPerPerson: '',
    guestNumber: '',
    totalBudget: ''
  });

  const [toastMessage, setToastMessage] = useState('');
  const [toastVariant, setToastVariant] = useState('success');
  const [showToast, setShowToast] = useState(false);

  const handleChange = (name, value) => {
    setBudget((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(`http://localhost:8080/api/budget/${eventId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ ...budget, eventId: Number(eventId) }), // Ensure eventId is a number if necessary
      });

      if (response.ok) {
        setToastVariant('success');
        setToastMessage('Budget updated successfully!');
        setShowToast(true);
        setTimeout(() => navigate(`/event/${eventId}`), 3000);
      } else {
        const errorMessage = await response.text();
        setToastVariant('danger');
        setToastMessage(`Failed to update budget: ${errorMessage}`);
        setShowToast(true);
      }
    } catch (error) {
      console.error('Error updating budget:', error);
      setToastVariant('danger');
      setToastMessage('Failed to update budget: An unexpected error occurred');
      setShowToast(true);
    }
  };

  return (
    <div className="container">
      <h1>Manage Budget for Event ID: {eventId}</h1>
      <form onSubmit={handleSubmit}>
        {/* Input fields for budget details */}
        <div className="mb-3">
          <label htmlFor="venueCost" className="form-label">Venue Cost</label>
          <input type="number" className="form-control" id="venueCost" name="venueCost" value={budget.venueCost} onChange={(e) => handleChange("venueCost", e.target.value)} />
        </div>
        {/* Add more fields for beverageCostPerPerson, guestNumber, and totalBudget similar to the above */}
        <div className="mb-3">
            <label htmlFor="beverageCostPerPerson" className="form-label">Beverage Cost Per Person</label>
            <input type="number" className="form-control" id="beverageCostPerPerson" name="beverageCostPerPerson" value={budget.beverageCostPerPerson} onChange={(e) => handleChange("beverageCostPerPerson", e.target.value)} />
        </div>
        <div className="mb-3">
            <label htmlFor="guestNumber" className="form-label">Guest Number</label>
            <input type="number" className="form-control" id="guestNumber" name="guestNumber" value={budget.guestNumber} onChange={(e) => handleChange("guestNumber", e.target.value)} />
        </div>

        <button type="submit" className="btn btn-primary">Submit Budget</button>
      </form>

      <CustomToast showToast={showToast} setShowToast={setShowToast} toastVariant={toastVariant} toastMessage={toastMessage} />
    </div>
  );
};

export default BudgetManagementPage;
