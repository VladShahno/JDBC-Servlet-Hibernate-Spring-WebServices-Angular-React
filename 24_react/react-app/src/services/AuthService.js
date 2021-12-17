import axios from "axios";

const API_URL = "http://localhost:8080/api/login";

class AuthService {
    login(username, password) {
        return axios
            .post(API_URL, {
                username,
                password
            })
            .then(response => {
                if (response.data.token) {
                    localStorage.setItem("user", JSON.stringify(response.data.user));
                }
                return response.data;
            });
    }

    logout() {
        localStorage.clear();
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }
}

export default new AuthService();