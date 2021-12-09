import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import {UserService} from "../service/user.service";


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private userService: UserService
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentRole = localStorage.getItem('role');
    if (currentRole && localStorage.getItem('jwtToken')) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
