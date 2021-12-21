import {Component, OnInit} from '@angular/core';
import {UserService} from "../../service/user.service";
import {UserForCreate} from "../../model/user-models/user-for-create";

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {

  role = localStorage.getItem('role');

  login = localStorage.getItem('user');

  UserName: string = '';

  user: UserForCreate = new UserForCreate();

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    if (typeof this.login === "string") {
      this.userService.getUserByLogin(this.login).subscribe(data => {
        this.user = data;
        this.UserName = this.user.firstName;
      }, error => console.log(error));
    }
  }
}
