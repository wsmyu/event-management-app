import React, {useState} from 'react';
import {Link} from 'react-router-dom';
import {useUser} from '../components/UserContext';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Form from 'react-bootstrap/Form';
import NavDropdown from 'react-bootstrap/NavDropdown'; // Import NavDropdown here
import {useNavigate} from 'react-router-dom';

const Header = () => {
    const {loggedInUser, handleLogout} = useUser();
    const navigate = useNavigate();
    const [searchWord, setSearchWord] = useState('');

    const handleSearch = (e) => {
        e.preventDefault();
        if (searchWord.trim() !== '') {
            setSearchWord('');
            navigate(`/search?eventName=${searchWord}`);
        }
    };

    return (
        <Navbar bg="dark" variant="dark" expand="lg" className="mb-3">
            <Container>
                <Navbar.Brand as={Link} to="/">Event Management Application</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav>
                        {loggedInUser && (
                            <>
                                <NavDropdown title="Event" id="basic-nav-dropdown">
                                    <NavDropdown.Item as={Link} to={`/create-event`}>Create Event</NavDropdown.Item>
                                    <NavDropdown.Item as={Link} to={`/invitations`}>Invitations</NavDropdown.Item>
                                </NavDropdown>
                                <NavDropdown title="Friend" id="basic-nav-dropdown">
                                    <NavDropdown.Item as={Link} to={`/user/friends`}>Friend List</NavDropdown.Item>
                                    <NavDropdown.Item as={Link} to={`/user/request`}>Friend Request</NavDropdown.Item>
                                    <NavDropdown.Item as={Link} to={`/user/search-friend`}>Search for
                                        Friend</NavDropdown.Item>
                                </NavDropdown>
                            </>
                        )}
                        {loggedInUser && (
                            <Nav.Link as={Link} to={`/feedback`}>Feedback</Nav.Link>
                        )}
                    </Nav>
                    <Form className="d-flex">
                        <Form.Control
                            type="search"
                            placeholder="Search"
                            className="me-2"
                            aria-label="Search"
                            value={searchWord}
                            onChange={(e) => setSearchWord(e.target.value)}
                        />
                        <button className="btn btn-outline-light" onClick={handleSearch}>Search</button>
                    </Form>
                    {loggedInUser ? (
                        <Nav>
                            <Nav.Link>Logged in as: {loggedInUser.username}</Nav.Link>
                            <Nav.Link onClick={handleLogout}>Logout</Nav.Link>
                        </Nav>
                    ) : (
                        <Nav>
                            <Nav.Link as={Link} to="/login">Login</Nav.Link>
                            <Nav.Link as={Link} to="/register">Register</Nav.Link>
                        </Nav>
                    )}
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Header;
