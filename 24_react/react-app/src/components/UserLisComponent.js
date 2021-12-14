import React, {useEffect, useState} from 'react'
import {Link} from 'react-router-dom'
import UserService from "../services/UserService";

const UserListComponent = () => {

    const [users, setUsers] = useState([])

    useEffect(() => {
        getAllUsers();
    }, [])

    const getAllUsers = () => {
        UserService.getAllUsers().then((response) => {
            setUsers(response.data)
            console.log(response.data);
        }).catch(error => {
            console.log(error);
        })
    }

    const deleteUser = (login) => {
        UserService.deleteUser(login).then((response) => {
                getAllUsers();
            }
        ).catch(error => {
            console.log(error);
        })
    }

    return (
        <div className="users_list">
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
                            <tr >
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
    )
}
export default UserListComponent