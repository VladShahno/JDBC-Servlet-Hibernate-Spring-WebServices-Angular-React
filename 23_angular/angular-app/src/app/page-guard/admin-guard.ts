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
export class AdminGuard implements CanActivate {
  constructor(
    private router: Router,
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentRole = localStorage.getItem('role')?.includes('ADMIN');
    if (currentRole && localStorage.getItem('jwtToken')) {
      return true;
    } else {
      this.router.navigate(['/home']);
      return false;
    }
  }


}
