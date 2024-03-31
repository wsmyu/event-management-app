import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import Form from 'react-bootstrap/Form';
import {useNavigate} from "react-router-dom";
import {useState} from "react";

function Header({ loggedInUser, onLogout }) {
    const navigate = useNavigate();
    const [searchWord,setSearchWord] = useState('');
    const handleSearch = (e) => {
        e.preventDefault();
        if (searchWord.trim() !== '') {
            navigate(`/search?eventName=${searchWord}`);
        }

    };
    return (
        <Navbar bg="dark" data-bs-theme="dark" expand="lg" className="bg-body-tertiary mb-3" >
            <Container>
                <Navbar.Brand href="/">Event Management Application</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/create-event">Create Event</Nav.Link>
                        <Nav.Link href="/user/create">Create User</Nav.Link>
                        <Nav.Link href="/user/login">Login</Nav.Link>
                    </Nav>
                    <Form className="d-flex">
                        <Form.Control
                            type="search"
                            placeholder="Search"
                            className="me-2"
                            aria-label="Search"
                            onChange={(e)=>setSearchWord(e.target.value)}
                        />
                        <button className="btn btn-outline-light" onClick={handleSearch}>Search</button>
                    </Form>
                    {loggedInUser && (
                                      <Nav>
                                        <Nav.Link href="/user/:id/friends">Friend List</Nav.Link>
                                        <Nav.Link disabled>Logged in as: {loggedInUser.username}</Nav.Link>
                                        <Nav.Link onClick={onLogout}>Logout</Nav.Link>
                                      </Nav>
                                    )}
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Header;