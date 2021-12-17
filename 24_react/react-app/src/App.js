import './App.css';
import {BrowserRouter as Router, Link, Route, Switch} from 'react-router-dom';
import UserListComponent from "./components/UserLisComponent";
import CreateUserComponent from "./components/CreateUserComponent";
import React from "react";
import NavBar from "./components/NavBar";
import UpdateUserComponent from "./components/UpdateUserComponent";
import LoginComponent from "./components/Login";

function App() {

    return (
        <div>
            <Router>
                <NavBar/>
                <div className="container">
                    <Switch>
                        <Route exact path = "/" component = {UserListComponent}/>
                        <Route exact path="/all" component={UserListComponent}/>
                        <Route path="/new" component={CreateUserComponent}/>
                        <Route path = "/update/:login" component = {UpdateUserComponent}/>
                        <Route path = "/login" component = {LoginComponent}/>
                    </Switch>
                </div>
            </Router>
        </div>
    );
}

export default App;