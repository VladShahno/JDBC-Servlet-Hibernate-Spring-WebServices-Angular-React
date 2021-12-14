import axios from 'axios'
import authHeader from "./AuthHeader";

const BASE_URL = 'http://localhost:8080/api/users';

const LOGIN_URL = 'http://localhost:8080/api';

class UserService {

    getAllUsers() {
        return axios.get(BASE_URL + '/all', {headers: authHeader()});
    }

    createUser(user) {
        return axios.post(BASE_URL, user)
    }

    getUserByLogin(login) {
        return axios.get(BASE_URL + '/' + login);
    }

    updateUser(login, user) {
        return axios.put((BASE_URL + '/' + login), user);
    }

    deleteUser(login) {
        return axios.delete(BASE_URL + '/' + login);
    }

}

export default new UserService();