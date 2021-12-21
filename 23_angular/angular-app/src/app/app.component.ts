import {Component} from '@angular/core';
import {UserService} from "./service/user.service";
import {UserForCreate} from "./model/user-models/user-for-create";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angular-app';

  role = localStorage.getItem('role');

  login = localStorage.getItem('user');

  UserName: string = '';

  user: UserForCreate = new UserForCreate();

  constructor(public userService: UserService) {
  }

  ngOnInit(): void {

    if (typeof this.login === "string") {
      this.userService.getUserByLogin(this.login).subscribe(data => {
        this.user = data;
        this.UserName = this.user.firstName;
      }, error => console.log(error));
    }
  }

  canBeShowed() {
    return this.userService.loggedIn() && localStorage.getItem('role') == "ADMIN";
  }
}
