import React from 'react';
import {Link} from 'react-router-dom';

const NavBar = () => {
    return (
        <div className="NavBar">
            <nav className="navbar navbar-light"
                 style={{background: "#e3f2fd"}}>
                <ul className="nav">
                    <li className="nav-item">
                        <Link to="/home" className="nav-link">Home</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/new" className="nav-link">Add User</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/logout" className="nav-link">Logout</Link>
                    </li>
                </ul>
            </nav>
        </div>
    );
};

export default NavBar;