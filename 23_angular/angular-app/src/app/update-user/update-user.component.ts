import {Component, OnInit} from '@angular/core';
import {UserForCreate} from "../user-models/user-for-create";
import {UserService} from "../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {

  user: UserForCreate = new UserForCreate();

  problems: Map<string, string> = new Map<string, string>();

  login!: string;

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.login = this.route.snapshot.params['login'];

    this.userService.getUserByLogin(this.login).subscribe(data => {
      this.user = data;
    }, error => console.log(error));
  }

  onSubmit() {
    this.userService.updateUser(this.login, this.user).subscribe({
      next: (data) => {
        console.log(data);
        this.goToUserList();
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

  goToUserList() {
    this.router.navigate(['/users/all']);
  }
}
