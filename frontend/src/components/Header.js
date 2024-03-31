import React from 'react';
import { Link } from 'react-router-dom';
import { useUser } from '../components/UserContext';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';

function Header() {
    const { loggedInUser, handleLogout } = useUser();

    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Container>
                <Navbar.Brand as={Link} to="/">Event Management Application</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/create-event">Create Event</Nav.Link>
                        <Nav.Link as={Link} to="/user/create">Create User</Nav.Link>
                        {loggedInUser ? (
                            <>
                                <Nav.Link as={Link} to={`/user/${loggedInUser.id}/friends`}>Friend List</Nav.Link>
                                <Nav.Link as={Link} to={`/user/${loggedInUser.id}/request`}>Friend Request</Nav.Link>
                            </>
                        ) : (
                            <Nav.Link as={Link} to="/user/login">Login</Nav.Link>
                        )}
                    </Nav>
                    {loggedInUser ? (
                        <Nav>
                            <Nav.Link>Logged in as: {loggedInUser.username}</Nav.Link>
                            <Nav.Link onClick={handleLogout}>Logout</Nav.Link>
                        </Nav>
                    ) : (
                        <Nav>
                            <Nav.Link as={Link} to="/user/login">Login</Nav.Link>
                        </Nav>
                    )}
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Header;
