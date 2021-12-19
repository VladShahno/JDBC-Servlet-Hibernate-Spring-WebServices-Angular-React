import React, {useState} from "react";
import UserService from "../../service/UserService";
import {useHistory} from 'react-router-dom';
import './LoginComponent.css';

const LoginComponent = () => {

    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [loginError, setLoinError] = useState(false);
    const history = useHistory();

    const onLogin = async (e) => {

        e.preventDefault();
        const user = {
            login,
            password
        }

        await UserService.loginUser(user).then((response) => {

            localStorage.setItem('token', response.data.token)
            localStorage.setItem('user', response.data.user)
            localStorage.setItem('role', response.data.role)

            if (localStorage.getItem('role') === 'USER') {
                history.push('/home');
            } else {
                history.push('/all')
            }
        }).catch(error => {
            setLoinError(true);
            console.log(error.response.data)
        })

    }

    return (
        <div className="login_block">
            <form onSubmit={onLogin} className="login_form">
                {loginError &&
                    <div className={"alert alert-danger error-window"}>
                        Wrong Login or Password!</div>}
                <label>Enter your login:</label>
                <input id="login" type="text" name="login"
                       className="form-control"
                       onChange={e => setLogin(e.target.value)} required/>
                <label>Enter your password:</label>
                <input type="password" name="password" id="password"
                       className="form-control"
                       onChange={e => setPassword(e.target.value)} required/>
                <div>
                    <button type="submit" className="btn btn-success"
                    >Sign in
                    </button>
                    <a href="/registration" className="btn btn-primary"
                       style={{marginTop: 10, width: 215, marginLeft: 5}}
                    >Registration</a>
                </div>
            </form>
        </div>
    )
}
export default LoginComponent;