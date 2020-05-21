import { Injectable } from '@angular/core';
import { HttpRequestService } from '../http-request/http-request.service';
import { Constants } from 'src/environments/constants';
import { User } from 'src/app/interface/user.interface';
import { DeliveryRequestService } from '../delivery-request/delivery-request.service';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { error } from 'protractor';
import { AuthService } from '../auth-Service/auth.service';
import { Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { ServiceResponseBase } from 'src/app/interface/service-response-base-interface';
import { TokenObject } from 'src/app/interface/token-object.interface';
import { AlertService } from '../alert/alert.service';
import { ToastrService } from 'ngx-toastr';
import { MatDialogRef, MatDialog } from '@angular/material';

@Injectable()
export class NewTokenService {

  httpHeader: HttpHeaders;
  constructor(private http: HttpClient, private router: Router, private toastr: ToastrService, private dialogRef: MatDialog) { }


  requestNewToken(): Promise<string> {


    return new Promise((resolve, reject) => {
      Constants.userData = JSON.parse(localStorage.getItem('userData'));
      if (!Constants.userData) {
        return;
      }

      this.get<ServiceResponseBase<TokenObject>>(Constants.CBK_NEW_TOKEN_URL, Constants.userData.refreshToken).subscribe(data => {


        if (!Constants.userData) {
          return;
        }
        const newUserData = new User(Constants.userData.id, Constants.userData.fullName, Constants.userData.username,
          Constants.userData.role, data.payLoad.token, data.payLoad.refreshToken, Constants.userData.consultant_flag, null);
        localStorage.setItem('userData', JSON.stringify(newUserData));

        Constants.userData = newUserData;

        resolve(data.payLoad.token);
      }, err => {
        localStorage.clear();
        Constants.user.next(null);
        this.router.navigate(['/login']);

        this.toastr.warning('Session Expired!');

        this.dialogRef.closeAll();

        // reject(false);
      }
      );
    });

  }


  private get<T>(url: string, token: string) {


    this.httpHeader = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + token
    });

    return this.http.get<T>(url, { headers: this.httpHeader });
  }
}
