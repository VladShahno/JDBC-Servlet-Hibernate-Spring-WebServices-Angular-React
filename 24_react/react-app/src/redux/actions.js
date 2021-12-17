
function fetchUser(user) {
    return fetch('http://localhost:8080/api/login', {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token'),
            'Content-Type': 'application/json'
        }, body: JSON.stringify({'username': user.username, 'password': user.password})
    })
}