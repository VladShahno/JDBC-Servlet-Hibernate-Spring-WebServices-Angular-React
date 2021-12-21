import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import UserService from "../../service/UserService";
import './NavBarComponent.css';

const NavBarComponent = () => {

    const role = localStorage.getItem('role');
    const token = localStorage.getItem('token');
    const login = localStorage.getItem('user');

    const [firstName, setFirstName] = useState('')

    useEffect(() => {
        UserService.getUserByLogin(login).then((response) => {
            setFirstName(response.data.firstName)
        })
    }, []);


    const showNavBar = () => {
        if (token) {
            if (role === 'USER') {
                return (
                    <div className="UserNavBar">
                        <nav className="navbar navbar-light"
                             style={{background: "#e3f2fd"}}>
                            <ul className="nav">
                                <li className="nav-item">
                                    <Link to="/home" className="nav-link">Home</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to="/login" onClick={() => {
                                        UserService.logout();
                                    }} className="nav-link">Logout</Link>
                                </li>
                            </ul>
                        </nav>
                        <h1 className="home_page">Hello, {firstName}!</h1>
                    </div>
                )
            } else {
                return (
                    <div className="AdminNavBar">
                        <nav className="navbar navbar-light"
                             style={{background: "#e3f2fd"}}>
                            <ul className="nav">
                                <li className="nav-item">
                                    <Link to="/all" className="nav-link">Home</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to="/new" className="nav-link">Add User</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to="/login" onClick={() => {
                                        UserService.logout();
                                    }} className="nav-link">Logout</Link>
                                </li>
                            </ul>
                        </nav>
                        <div className="loggedUser">
                            <p>
                                <mark>{role} {firstName}</mark>
                            </p>
                        </div>
                    </div>
                )
            }
        } else {
            return null;
        }
    }

    return (
        showNavBar()
    );
}

export default NavBarComponent;