import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserListComponent} from "./user-list/user-list.component";
import {CreateUserComponent} from "./create-user/create-user.component";
import {UpdateUserComponent} from "./update-user/update-user.component";
import {LoginFormComponent} from "./login-form/login-form.component";
import {AuthGuard} from "./page-guard/auth-guard";
import {AdminGuard} from "./page-guard/admin-guard";
import {UserHomeComponent} from "./user-home/user-home.component";

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
  {path: 'home', component: UserHomeComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
