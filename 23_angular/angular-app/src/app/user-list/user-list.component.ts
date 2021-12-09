import {Component, OnInit} from '@angular/core';
import {UserService} from "../service/user.service";
import {User} from "../user-models/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[] = [];

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
    this.getUsers();
  }

  private getUsers() {
    this.userService.getUsersList().subscribe(data => {
      this.users = data;
    });
  }

  updateUser(login: string) {
    this.router.navigate(['users/update', login]);
  }

  deleteEmployee(login: string) {
    this.userService.deleteUser(login).subscribe(data => {
      console.log(data);
      this.getUsers();
    })
  }

  confirmDelete(login: string) {
    if (confirm("Are you sure to delete ")) {
      this.deleteEmployee(login);
    }
  }
}
