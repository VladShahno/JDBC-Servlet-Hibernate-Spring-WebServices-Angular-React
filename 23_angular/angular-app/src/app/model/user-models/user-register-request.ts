export class UserRegisterRequest {

  login!: string;
  email!: string;
  firstName!: string;
  lastName!: string;
  birthday!: number;
  password!: string;
  passwordConfirm!: string;
  role!: string;
  recaptchaResponse!: string;
}
