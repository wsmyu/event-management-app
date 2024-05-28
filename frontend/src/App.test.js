import { render, screen } from '@testing-library/react';
import App from './App';
import { BrowserRouter as Router } from 'react-router-dom';
// Mock the useNavigate hook
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'), // Use the actual implementation for other hooks
  useNavigate: () => jest.fn(), // Mock useNavigate to return a dummy function
}));

test('renders main page', () => {
  render(
      <Router>
        <App />
      </Router>);

});
