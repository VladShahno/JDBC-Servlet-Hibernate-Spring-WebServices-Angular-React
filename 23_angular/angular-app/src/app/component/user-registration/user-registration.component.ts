import {Component, OnInit} from '@angular/core';
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {UserRegisterRequest} from "../../model/user-models/user-register-request";
import {FormBuilder} from "@angular/forms";

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent implements OnInit {

  user: UserRegisterRequest = new UserRegisterRequest();


  problems: Map<string, string> = new Map<string, string>();

  errorMassage: string = '';

  submitted: boolean = false;
  captchaError: boolean = false;
  RegisterResponse!: string;

  constructor(private userService: UserService,
              private router: Router, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
  }

  saveUser() {

    this.userService.registerUser(this.user).subscribe({
      next: () => {
        this.goToLoginPage();
      },
      error: error => {
        this.problems.clear();
        for (const [key, value] of Object.entries(error.error)) {
          this.problems.set(key, <string>value);
        }
        grecaptcha.reset();
      }
    });
  }

  goToLoginPage() {
    this.router.navigate(['/login']);
  }

  onSubmit() {
    const response = grecaptcha.getResponse();
    if (response.length === 0) {
      this.captchaError = true;
      return;
    }
    this.user.recaptchaResponse = response;
    this.saveUser();
  }
}
