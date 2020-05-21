import { Injectable, OnInit } from '@angular/core';
import { HttpRequestService } from '../http-request/http-request.service';
import { ServerResponse } from 'http';
import { ServiceResponseBase } from 'src/app/interface/service-response-base-interface';
import { Constants } from 'src/environments/constants';
import { User } from 'src/app/interface/user.interface';
import { resolve } from 'url';

@Injectable({
  providedIn: 'root'
})
export class RolesService {
  userRole: string;



  constructor(private httpRequest: HttpRequestService) {
  }


  async retrieveRoleCode(userData) {
    if(userData){
    await this.httpRequest.post<ServiceResponseBase<string>>(Constants.CBK_USER_ROLE_CODE_URL, userData.username, true).then(
      data => {
        if (data.payLoad === 'AD' || data.payLoad === 'SA') {
          this.userRole = 'level-1';
        } else if (data.payLoad === 'RM') {
          this.userRole = 'level-2';
        } else {
          this.userRole = 'level-3';
        }
      });
    return this.userRole;
  }
  }

  async retrieveRole(userData) {
    if(userData){
    await this.httpRequest.post<ServiceResponseBase<string>>(Constants.CBK_USER_ROLE_URL, userData.username, true).then(
      data => {
       this.userRole=data.payLoad;
      });
    return this.userRole;
  }
  }
}
