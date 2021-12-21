import axios from 'axios'

const BASE_URL = 'http://localhost:8080/api/';

class UserService {

    getAllUsers() {
        return axios.get(BASE_URL + 'users/all', {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        });
    }

    createUser(user) {
        return axios.post(BASE_URL + 'users', user, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        });
    }

    getUserByLogin(login) {
        return axios.get(BASE_URL + 'users/' + login, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        });
    }

    updateUser(login, user) {
        return axios.put((BASE_URL + 'users/' + login), user, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        });
    }

    deleteUser(login) {
        return axios.delete(BASE_URL + 'users/' + login, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        });
    }

    loginUser(user) {
        return axios.post(BASE_URL + 'login', {'login': user.login, 'password': user.password}, {
            headers: {
                'Content-Type': 'application/json',
            }
        });
    }

    logout() {
        localStorage.clear();
    }

    registerUser(user) {
        return axios.post(BASE_URL + 'registration', user, {
            headers: {
                'Content-Type': 'application/json',
            }
        });
    }
}

export default new UserService();
