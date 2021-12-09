import {Component, OnInit} from '@angular/core';
import {UserForCreate} from "../user-models/user-for-create";
import {UserService} from "../service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

  user: UserForCreate = new UserForCreate();
  problems: Map<string, string> = new Map<string, string>();

  constructor(private userService: UserService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  saveUser() {
    this.userService.createUser(this.user).subscribe({
      next: (data) => {
        console.log(data);
        this.goToUsersList();
      },
      error: error => {
        this.problems.clear();
        for (const [key, value] of Object.entries(error.error)) {
          this.problems.set(key, <string>value);
        }
        console.log(error.error);
      }
    })
  }

  goToUsersList() {
    this.router.navigate(['/users/all']);
  }

  onSubmit() {
    console.log(this.user);
    this.saveUser();
  }
}
