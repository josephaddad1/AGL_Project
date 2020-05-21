import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { ErrorHandelerService } from '../error-handel/error-handeler.service';
import { User } from 'src/app/interface/user.interface';
import { NewTokenService } from '../new-token/new-token.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import * as jwt_decode from 'jwt-decode';
import { Constants } from 'src/environments/constants';

@Injectable()
export class HttpRequestService {

  httpHeader: HttpHeaders;
  httpParam: HttpParams;
  token: string;
  constructor(private http: HttpClient, private errorHandel: ErrorHandelerService, private newTokenService: NewTokenService,
    private router: Router, private toastr: ToastrService) {

  }

  async post<T>(url: string, body: any, tokenRequired?: boolean, httpParam?: HttpParams, token?: string): Promise<T> {

    await this.prepareRequest(tokenRequired, token);

    return new Promise((resolve, reject) => {
      this.http.post<T>(url, body, { headers: this.httpHeader, params: httpParam }).pipe(
        catchError(this.errorHandel.handleError)

      ).subscribe(data => { resolve(data); }
        , error => {
          reject(error);
        });

    });


  }

  async get<T>(url: string, tokenRequired?: boolean, httpParam?: HttpParams): Promise<T> {
    await this.prepareRequest(tokenRequired);

    return new Promise((resolve, reject) => {
      this.http.get<T>(url, { headers: this.httpHeader, params: httpParam }).pipe(
        catchError(this.errorHandel.handleError)
      ).subscribe(data => { resolve(data); }, error => {
        reject(error);
      });
    });
  }

  async delete<T>(url: string, tokenRequired?: boolean, httpParam?: HttpParams): Promise<T> {
    await this.prepareRequest(tokenRequired);
    return new Promise((resolve, reject) => {
      this.http.delete<T>(url, { headers: this.httpHeader, params: httpParam }).pipe(
        catchError(this.errorHandel.handleError)
      ).subscribe(data => { resolve(data); }, error => {
        reject(error);
      });
    });
  }

  private async prepareRequest(tokenRequired: boolean, token?: string) {
    if (tokenRequired) {
      if (this.isTokenExpired()) {
        this.token = await this.newTokenService.requestNewToken();
      }
      if (!this.token == null) {
        return;
      }

      if (token) {
        this.token = token;
      }
      this.httpHeader = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + this.token
      });
    } else {
      this.httpHeader = new HttpHeaders({
        'Content-Type': 'application/json'
      });
    }
  }


  private isTokenExpired(): boolean {

    Constants.userData = JSON.parse(localStorage.getItem('userData'));
    if (Constants.userData) {
      this.token = Constants.userData.token;
    }

    if (!this.token) {
      return;
    }
    const decodedToken = jwt_decode(this.token);
    const date = new Date(0);

    const tokenExpDate = date.setUTCSeconds(decodedToken.exp);

    if (tokenExpDate.valueOf() < new Date().valueOf()) {
      return true;
    }
    return false;
  }

}
