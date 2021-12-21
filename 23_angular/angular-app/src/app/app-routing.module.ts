import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserListComponent} from "./component/user-list/user-list.component";
import {CreateUserComponent} from "./component/create-user/create-user.component";
import {UpdateUserComponent} from "./component/update-user/update-user.component";
import {LoginFormComponent} from "./component/login-form/login-form.component";
import {AuthGuard} from "./component/page-guard/auth-guard";
import {AdminGuard} from "./component/page-guard/admin-guard";
import {UserHomeComponent} from "./component/user-home/user-home.component";
import {UserRegistrationComponent} from "./component/user-registration/user-registration.component";

const routes: Routes = [
  {
    path: 'users/all',
    component: UserListComponent,
    canActivate: [AuthGuard, AdminGuard]
  },
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {
    path: 'users/new',
    component: CreateUserComponent,
    canActivate: [AuthGuard, AdminGuard]
  },
  {
    path: 'users/update/:login',
    component: UpdateUserComponent,
    canActivate: [AuthGuard, AdminGuard]
  },
  {path: 'login', component: LoginFormComponent},
  {path: 'home', component: UserHomeComponent, canActivate: [AuthGuard]},
  {path: 'registration', component: UserRegistrationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
