import { Injectable } from '@angular/core';
import { HttpRequestService } from '../http-request/http-request.service';
import { ServiceResponseBase } from 'src/app/interface/service-response-base-interface';
import { Constants } from 'src/environments/constants';
import { DeliveryView } from 'src/app/interface/DeliveryView.interface';
import { Page } from 'src/app/interface/Paging/page.interface';
import { User } from 'src/app/interface/user.interface';
import { NewTokenService } from '../new-token/new-token.service';
import { HttpParams } from '@angular/common/http';
import { ErrorDetails } from 'src/app/interface/error-details.interface';
import { CustomIdFullName } from 'src/app/interface/custom-id-full-name.interface';


export interface HashMap {
  [details: string]: Array<CustomIdFullName>;
}



@Injectable()
export class DeliveryRequestService {

  token: string;

  constructor(private http: HttpRequestService, private newTokenService: NewTokenService) {


  }

  // initialisation requests
  getDeliveryInit(value: string, type: string) {

    let httpParam = new HttpParams();
    httpParam = httpParam.append('value', value);
    httpParam = httpParam.append('type', type);


    return this.http.get<ServiceResponseBase<HashMap>>(Constants.CBK_DELIVERY_URL + '/initialise', true, httpParam);
  }

  deleteDelivery(deliveryId: any) {


    let httpParam = new HttpParams();
    httpParam = httpParam.append('deliveryId', deliveryId);

    return this.http.delete<ServiceResponseBase<string>>(Constants.CBK_DELETE_DELIVERY_URL, true, httpParam)
  }

  getTypes() {
    return this.http.get<ServiceResponseBase<Array<CustomIdFullName>>>(Constants.CBK_DELIVERY_URL + '/getType', true);
  }

  // search request

  getDeliveries(body, page: number, pageSize: number): Promise<ServiceResponseBase<Page<DeliveryView>>> {

    let httpParam = new HttpParams();
    httpParam = httpParam.append('page', page.toString());
    httpParam = httpParam.append('pageSize', pageSize.toString());

    return this.http.post<ServiceResponseBase<Page<DeliveryView>>>(Constants.CBK_DELIVERY_URL
      + '/search', body, true, httpParam);
  }

  // insert request
  insertDelivery(body,httpParam): Promise<any> {
    return this.http.post(Constants.CBK_DELIVERY_URL + '/insert', body, true,httpParam);
  }

}
