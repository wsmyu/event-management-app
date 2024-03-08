import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';

function Header({ loggedInUser, onLogout }) {
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