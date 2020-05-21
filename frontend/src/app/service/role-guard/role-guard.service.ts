import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { RolesService } from '../roles-service/roles.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService {

  constructor(private router: Router, private roleService: RolesService) { }


  canActivate(
    route: ActivatedRouteSnapshot,
    router: RouterStateSnapshot
  ):
    | boolean | UrlTree | Promise<boolean | UrlTree> | Observable<boolean | UrlTree> {


    if (this.roleService.userRole === 'level-1') {
      return true;
    } else {
      return this.router.createUrlTree(['/home']);
    }

  }
}
