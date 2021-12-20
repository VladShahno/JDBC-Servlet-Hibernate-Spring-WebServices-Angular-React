import './App.css';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import UserListComponent from "./component/list-user/UserListComponent";
import CreateUserComponent from "./component/create-user/CreateUserComponent";
import React from "react";
import UpdateUserComponent from "./component/update-user/UpdateUserComponent";
import LoginComponent from "./component/login-user/LoginComponent";
import UserHomeComponent from "./component/home-user/UserHomeComponent";
import RegistrationComponent
    from "./component/registration-user/RegistrationComponent";
import AdminRouteGuard from "./component/route/AdminRouteGuard";

function App() {

    const role = localStorage.getItem('role');
    const token = localStorage.getItem('token');

    const isAuthorizedAsAdmin = () => {
        if (token && role === "ADMIN") {
            return true
        }
    }

    const isAuthorizedAsUser = () => {
        if (token && role === "USER") {
            return true
        }
    }

    return (
        <div>
            <Router>
                <div>
                    <switch>
                        <Route path="/login" component={LoginComponent}/>
                        <AdminRouteGuard path='/new'
                                         component={CreateUserComponent}
                                         auth={isAuthorizedAsAdmin()}/>
                        <AdminRouteGuard path="/home"
                                         component={UserHomeComponent}
                                         auth={isAuthorizedAsUser()}/>
                        <AdminRouteGuard exact path="/all"
                                         component={UserListComponent}
                                         auth={isAuthorizedAsAdmin()}/>
                        <AdminRouteGuard path="/update/:login"
                                         component={UpdateUserComponent}
                                         auth={isAuthorizedAsAdmin()}/>
                        <Route path="/registration"
                               component={RegistrationComponent}/>
                    </switch>
                </div>
            </Router>
        </div>
    );
}

export default App;