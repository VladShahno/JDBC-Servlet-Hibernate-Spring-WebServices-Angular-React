import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {UserListComponent} from './component/user-list/user-list.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {UserService} from "./service/user.service";
import {CreateUserComponent} from './component/create-user/create-user.component';
import {UpdateUserComponent} from './component/update-user/update-user.component';
import {LoginFormComponent} from './component/login-form/login-form.component';
import {UserHomeComponent} from './component/user-home/user-home.component';
import {UserRegistrationComponent} from './component/user-registration/user-registration.component';
import {RecaptchaFormsModule, RecaptchaModule} from 'ng-recaptcha';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    CreateUserComponent,
    UpdateUserComponent,
    LoginFormComponent,
    UserHomeComponent,
    UserRegistrationComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    RecaptchaModule,
    RecaptchaFormsModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})

export class AppModule {


}
