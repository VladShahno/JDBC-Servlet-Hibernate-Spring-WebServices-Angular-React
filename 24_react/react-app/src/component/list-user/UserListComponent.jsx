import React, {useEffect, useState} from 'react'
import {Link} from 'react-router-dom'
import UserService from "../../service/UserService";
import NavBarComponent from "../navbar-user/NavBarComponent";

const UserListComponent = () => {

    const [users, setUsers] = useState([])

    useEffect(() => {
        getAllUsers();
    }, [])

    const getAllUsers = () => {
        UserService.getAllUsers().then((response) => {
            setUsers(response.data)
        })
    }

    const deleteUser = (login) => {
        UserService.deleteUser(login).then((response) => {
                getAllUsers();
            }
        )
    }

    return (
        <div>
            {NavBarComponent()}
            <div className="container">
                <h2 className="text-center"> Users List </h2>
                <table className="table table-bordered">
                    <thead>
                    <th> Login</th>
                    <th> First Name</th>
                    <th> Last Name</th>
                    <th> Age</th>
                    <th> Role</th>
                    <th> Actions</th>
                    </thead>
                    <tbody>
                    {
                        users.map(
                            user =>
                                <tr>
                                    <td> {user.login} </td>
                                    <td>{user.firstName}</td>
                                    <td>{user.lastName}</td>
                                    <td>{user.age}</td>
                                    <td>{user.role}</td>
                                    <td>
                                        <Link className="btn btn-info"
                                              to={`/update/${user.login}`}>Update</Link>
                                        <button className="btn btn-danger"
                                                onClick={() => {
                                                    if (window.confirm('Delete this user ?')) {
                                                        deleteUser(user.login)
                                                    }
                                                }}
                                                style={{marginLeft: "10px"}}> Delete
                                        </button>
                                    </td>
                                </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    )
}
export default UserListComponent