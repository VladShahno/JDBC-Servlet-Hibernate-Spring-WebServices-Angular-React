import React, {useEffect, useState} from 'react'
import {Link, useHistory, useParams} from 'react-router-dom';
import UserService from "../services/UserService";
import {useForm} from "react-hook-form";

const AddEmployeeComponent = () => {

    const [login, setLogin] = useState('');
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [passwordConfirm, setPasswordConfirm] = useState('')
    const [birthday, setBirthday] = useState('')
    const [role, setRole] = useState('')
    const history = useHistory();
    const {login: login_update} = useParams();
    const {formState: {errors}, handleSubmit} = useForm();
    const [problems, setProblems] = useState(new Map());

    const UpdateUser = () => {

        const user = {
            login: login_update,
            firstName,
            lastName,
            email,
            password,
            passwordConfirm,
            birthday,
            role
        }
        console.log(user);

        UserService.updateUser(login_update, user).then((response) => {

            if (response.status === 200) {
                history.push('/all')
            } else {
                console.log(response.data)
                setProblems(response.data)
            }
        }).catch(error => {
            console.log(error.response.data)
            setProblems(error.response.data)
        })
    }
    useEffect(() => {

        UserService.getUserByLogin(login_update).then((response) => {
            setLogin(response.data.login)
            setFirstName(response.data.firstName)
            setLastName(response.data.lastName)
            setEmail(response.data.email)
            setPassword(response.data.password)
            setPasswordConfirm(response.data.passwordConfirm)
            setBirthday(response.data.birthday)
            setRole(response.data.role)
        }).catch(error => {
            console.log(error)
        })
    }, []);

    return (
        <div>
            <div className="container">
                <div className="row">
                    <div className="col-md-6 offset-md-3">
                        <h2 className={"text-center"}>Update User</h2>
                        <div className="card-body">
                            <form onSubmit={handleSubmit(UpdateUser)}>

                                <div className="form-group mb-2">
                                    <label className="form-label"> Login
                                        :</label>
                                    <input
                                        type="text"
                                        name="login"
                                        className="form-control"
                                        value={login_update} readOnly={true}>
                                    </input>
                                </div>

                                <div className="form-group mb-2">
                                    {
                                        problems.nameError &&
                                        <div
                                            className="alert alert-warning">{problems.nameError}</div>
                                    }
                                    <label className="form-label"> First Name
                                        :</label>
                                    <input
                                        type="text"
                                        placeholder="Enter First name"
                                        name="firstName"
                                        className="form-control"
                                        value={firstName}
                                        onChange={(e) => setFirstName(e.target.value)}
                                        required={true}>
                                    </input>
                                </div>

                                <div className="form-group mb-2">
                                    <label className="form-label"> Last Name
                                        :</label>
                                    <input
                                        type="text"
                                        placeholder="Enter last name"
                                        name="lastName"
                                        className="form-control"
                                        value={lastName}
                                        onChange={(e) => setLastName(e.target.value)}
                                        required={true}>
                                    </input>
                                </div>

                                <div className="form-group mb-2">
                                    <label className="form-label"> Email
                                        :</label>
                                    <input
                                        type="email"
                                        placeholder="Enter email "
                                        name="email"
                                        className="form-control"
                                        value={email}
                                        onChange={(e) => setEmail(e.target.value)}
                                        required={true}>
                                    </input>
                                    {
                                        problems.emailError &&
                                        <div
                                            className="alert alert-warning">{problems.emailError}</div>
                                    }
                                </div>

                                <div className="form-group mb-2">
                                    <label className="form-label"> Password
                                        :</label>
                                    <input
                                        type="password"
                                        placeholder="Enter password "
                                        name="password"
                                        className="form-control"
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        required={true}>
                                    </input>
                                </div>

                                <div className="form-group mb-2">
                                    <label className="form-label"> Confirm
                                        Password
                                        :</label>
                                    <input
                                        type="password"
                                        placeholder="Confirm Password "
                                        name="passwordConfirm"
                                        className="form-control"
                                        value={passwordConfirm}
                                        onChange={(e) => setPasswordConfirm(e.target.value)}
                                        required={true}>
                                    </input>
                                    {
                                        problems.passwordError &&
                                        <div
                                            className="alert alert-warning">{problems.passwordError}</div>
                                    }
                                </div>

                                <div className="form-group mb-2">
                                    <label className="form-label">
                                        Birthday
                                        :</label>
                                    <input
                                        type="date"
                                        placeholder="Enter Birthday "
                                        name="passwordConfirm"
                                        className="form-control"
                                        value={birthday}
                                        onChange={(e) => setBirthday(e.target.value)}
                                        required={true}>
                                    </input>
                                    {
                                        problems.dateError &&
                                        <div
                                            className="alert alert-warning">{problems.dateError}</div>
                                    }
                                </div>

                                <div className="form-group mb-2">
                                    <label className="form-label">
                                        Role :</label>
                                    <select value={role}
                                            onChange={(e) => setRole(e.target.value)}
                                            className="form-control"
                                            required={true}>
                                        <option value="USER">USER</option>
                                        <option value="ADMIN">ADMIN</option>
                                    </select>
                                </div>

                                <div>
                                    <button type={"submit"}
                                            className="btn btn-success"> Update
                                    </button>
                                    <Link to="/all"
                                          className="btn btn-outline-danger">Cancel</Link>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>)
}

export default AddEmployeeComponent