import React, {Component} from 'react';
import {Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink} from 'reactstrap';
import {Link} from 'react-router-dom';

export default class AppNavbar extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isOpen: false
        };
        this.toggle = this.toggle.bind(this);
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        })
    }

    render() {
        return (
            <Navbar color="dark" dark expand="md">
                <NavbarBrand tag={Link} to="/">Home</NavbarBrand>
                <NavbarToggler onClick={this.toggle}/>
                <Nav navbar>
                    <NavItem>
                        <NavLink tag={Link} to="/books">
                            Books
                        </NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink tag={Link} to="/authors">
                            Authors
                        </NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink tag={Link} to="/genres">
                            Genres
                        </NavLink>
                    </NavItem>
                </Nav>
            </Navbar>
        )
    }

}