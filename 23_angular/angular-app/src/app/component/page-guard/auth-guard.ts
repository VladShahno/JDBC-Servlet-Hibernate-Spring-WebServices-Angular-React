import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot
} from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {
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
