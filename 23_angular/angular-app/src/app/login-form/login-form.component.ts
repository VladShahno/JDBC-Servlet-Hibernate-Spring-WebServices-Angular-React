import {Component, OnInit} from '@angular/core';
import {UserService} from "../service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  constructor(public service: UserService, private router: Router) {
  }

  login: string = '';
  password: string = '';
  loginMassage: string = 'Enter your Login and Password';

  postLoginData() {
    this.service.loginUser({
      login: this.login,
      password: this.password
    }).subscribe(
      tokenResponse => {
        localStorage.setItem('jwtToken', tokenResponse.token)
        localStorage.setItem('role', tokenResponse.role);
        localStorage.setItem('user', tokenResponse.user);
        this.redirectUser();
      });
    if (localStorage.getItem('user') == null) {
      this.loginMassage = 'Wrong Login or Password!';
    } else {
      this.redirectUser();
    }
  }

  ngOnInit(): void {
  }

  goToAdminPage() {
    this.router.navigate(['/users/all']);
  }

  redirectUser() {
    const userRole = localStorage.getItem('role');
    if (userRole == 'USER') {
      this.router.navigate(['/home']);
    } else if (userRole == 'ADMIN') {
      this.goToAdminPage();
    }
  }
}
