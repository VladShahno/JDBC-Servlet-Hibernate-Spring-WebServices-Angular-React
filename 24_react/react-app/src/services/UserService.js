import axios from 'axios'
import authHeader from "./AuthHeader";

const BASE_URL = 'http://localhost:8080/api/users';

const LOGIN_URL = 'http://localhost:8080/api';


class UserService {

    getAllUsers() {
        return axios.get(BASE_URL + '/all', {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        });
    }

    createUser(user) {
        return axios.post(BASE_URL, user, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        });
    }

    getUserByLogin(login) {
        return axios.get(BASE_URL + '/' + login, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        });
    }

    updateUser(login, user) {
        return axios.put((BASE_URL + '/' + login), user, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        });
    }

    deleteUser(login) {
        return axios.delete(BASE_URL + '/' + login, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        });
    }

    loginUser(user) {
        return axios.post(LOGIN_URL + '/login',{'login':user.login,'password': user.password}, {
            headers: {
                'Content-Type': 'application/json',
            }
        });
    }

    logout() {
        localStorage.clear();
    }
}

export default new UserService();
