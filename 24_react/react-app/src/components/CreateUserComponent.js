import React, {useState} from 'react'
import {Link, useHistory} from 'react-router-dom';
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
    const {formState: {errors}, handleSubmit} = useForm();
    const [problems, setProblems] = useState(new Map());

    const saveUser = () => {

        const user = {
            login,
            firstName,
            lastName,
            email,
            password,
            passwordConfirm,
            birthday,
            role
        }
        console.log(user);

        UserService.createUser(user).then((response) => {
            if (response.status === 201) {
                history.push('/all')
            } else {
                console.log(response.data)
                setProblems(response.data)
            }
        }).catch(error => {
            console.log(error.response.data)
            debugger
            setProblems(error.response.data)
        })
    }

    return (
        <div>
            <div className="container">
                <div className="row">
                    <div className="col-md-6 offset-md-3">
                        <h2 className={"text-center"}>Create User</h2>
                        <div className="card-body">
                            <form onSubmit={handleSubmit(saveUser)}>
                                <div className="form-group mb-2">
                                    <label className="form-label"> Login
                                        :</label>
                                    <input
                                        type="text"
                                        placeholder="Enter Login"
                                        name="login"
                                        className="form-control"
                                        value={login}
                                        onChange={(e) => setLogin(e.target.value)}
                                        required={true}>
                                    </input>
                                    {
                                        problems.loginError &&
                                        <div
                                            className="alert alert-warning">{problems.loginError}</div>
                                    }
                                    {
                                        problems.constraintLoginError &&
                                        <div
                                            className="alert alert-warning">{problems.constraintLoginError}</div>
                                    }
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
                                        <option value=""/>
                                        <option value="USER">USER</option>
                                        <option value="ADMIN">ADMIN</option>
                                    </select>
                                </div>

                                <div>
                                    <button type={"submit"}
                                            className="btn btn-success"> Create
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