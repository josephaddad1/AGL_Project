import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import { Observable } from 'rxjs';
import { map, take } from 'rxjs/operators';
import { AuthService } from '../auth-Service/auth.service';
import { Constants } from 'src/environments/constants';


@Injectable()
export class LoggedInGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    router: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Promise<boolean | UrlTree>
    | Observable<boolean | UrlTree> {

    Constants.userData = JSON.parse(localStorage.getItem('userData'));

    // if  logged in go to home


    if (!Constants.userData) {
      return true;
    } else {
      return this.router.createUrlTree(['/home']);
    }
    // TODO modify
    // return this.authService.user.pipe(
    //   take(1),
    //   map(user => {
    //     const isAuth = !!user;
    //     if (isAuth) {
    //       return true;
    //     }
    //     return this.router.createUrlTree(['/login']);
    //   }));
  }
}
