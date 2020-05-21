import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { User } from 'src/app/interface/user.interface';
import { catchError, tap, map } from 'rxjs/operators';
import { ServiceResponseBase } from 'src/app/interface/service-response-base-interface';
import { HttpRequestService } from '../http-request/http-request.service';
import { Constants } from 'src/environments/constants';
import { ErrorDetails } from 'src/app/interface/error-details.interface';
import { ErrorHandelerService } from '../error-handel/error-handeler.service';
import { UserLoginResponse } from 'src/app/interface/user-login-response.interface';
import { TokenObject } from 'src/app/interface/token-object.interface';
import { MatDialog } from '@angular/material';
import { RolesService } from '../roles-service/roles.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  constructor(private http: HttpRequestService, private router: Router, private httpRequest: HttpRequestService,
    private errorHandel: ErrorHandelerService,private dialogRef: MatDialog, private roleService: RolesService 
    ,private ngModel:NgbModal,private httpClient:HttpClient,private toast: ToastrService,) {


  }
  static user: BehaviorSubject<User> = new BehaviorSubject(null);
  newUser: User = null;

  login(username: string, password: string) {
    const url: string = Constants.CBK_AUTH_URL;

    return this.httpRequest.post<ServiceResponseBase<UserLoginResponse>>(url, {
      username,
      password
    }).then(
      response => {
        this.handleAuthentication(
          response.payLoad
        );
        this.router.navigate(['/home']);
      });

  }


  private  handleAuthentication(
    userData: UserLoginResponse

  ) {
    this.newUser = new User(userData.id, userData.fullName, userData.username
      , userData.role, userData.tokenObject.token, userData.tokenObject.refreshToken, userData.consultant, null);
    Constants.user.next(this.newUser);
    Constants.userData = this.newUser;

    localStorage.setItem('userData', JSON.stringify(this.newUser));
    localStorage.setItem('logInDate', JSON.stringify(new Date()));

    this.adminRole();


  }

  logout() {
    Constants.user.next(null);
    localStorage.clear();

    this.dialogRef.closeAll();
    this.ngModel.dismissAll();
    this.router.navigate(['/login']);
    



  }
   autoLogin() {

    Constants.userData = JSON.parse(localStorage.getItem('userData'));


    if (!Constants.userData) {
      return;
    }

    const loInDate: Date = JSON.parse(localStorage.getItem('logInDate'));

    const now = new Date().getTime();
    const date = new Date(loInDate).getTime();
    if (now - date > Constants.SESSION_TIME * 1000) {
      
      this.logout();
      return;
    }
this.adminRole();
    

    const loadedUser = new User(Constants.userData.id, Constants.userData.fullName, Constants.userData.username,
      Constants.userData.role, Constants.userData.token, Constants.userData.refreshToken, Constants.userData.consultant_flag, null);
    Constants.user.next(loadedUser);

  }

  async adminRole(){
    await this.roleService.retrieveRoleCode(Constants.userData).then(role => {
      role;
   let isAdmin = role === 'level-1' ? true : false;
     Constants.isAdmin.next(isAdmin);
   });
  }

  requestResetPassword(username:string){
    let httpParam = new HttpParams();
    httpParam = httpParam.append('username', username);


    this.httpClient.get(Constants.CBK_REQUEST_RESET_PASSWORD_URL, { params: httpParam })
      .subscribe(data => {
        
          this.toast.success('An email has been sent to your email to change password', 'Reset Password');
          this.dialogRef.closeAll();
        
      }, error => {
        (error);
      });
  }

  resetPassword(password: string, confPass: string, token: string) {
    let httpParam = new HttpParams();
    httpParam = httpParam.append('password', password);
    httpParam = httpParam.append('confPassword', confPass);
    return this.http.post(Constants.CBK_RESET_PASSWORD_URL, {}, true, httpParam, token);
  }

  changePassword(oldPassword: string, password: string, confPass: string) {
    let httpParam = new HttpParams();
    httpParam = httpParam.append('oldPassword', oldPassword);
    httpParam = httpParam.append('password', password);
    httpParam = httpParam.append('confPassword', confPass);
    return this.http.post(Constants.CBK_CHAMGE_PASSWORD_URL, {}, true, httpParam);
  }


}
