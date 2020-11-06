import React, { Component } from 'react'
import { Link } from "react-router-dom";
import { Navbar, Nav } from "react-bootstrap";

class HeaderComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {

        }
    }

    render() {
        return (
            <Navbar bg="dark" variant="dark" expand="lg">
                <Navbar.Brand href="/">Auction Hunter</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Link className="nav-item nav-link" to="/">Targets</Link>
                        <Link className="nav-item nav-link" to="/add">Add target</Link>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        )
    }
}

export default HeaderComponent