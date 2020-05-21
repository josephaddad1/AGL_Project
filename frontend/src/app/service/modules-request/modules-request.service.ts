import { Injectable } from '@angular/core';
import { HttpRequestService } from '../http-request/http-request.service';
import { ServiceResponseBase } from 'src/app/interface/service-response-base-interface';
import { CustomIdFullName } from 'src/app/interface/custom-id-full-name.interface';
import { Constants } from 'src/environments/constants';
import { HttpParams } from '@angular/common/http';
import { SubModuleApp } from 'src/app/interface/SubModuleApp.interface';
import { ModuleView } from 'src/app/interface/moduleView.interface';
import { Pageable } from 'src/app/interface/Paging/pageable.interface';
import { Page } from 'src/app/interface/Paging/page.interface';

@Injectable({
  providedIn: 'root'
})
export class ModulesRequestService {

  constructor(private http: HttpRequestService) { }



  getModules(value: string) {

    const httpParam = new HttpParams().append('moduleName', value);
    return this.http.get<ServiceResponseBase<Array<CustomIdFullName>>>(Constants.CBK_MODULES_LIST_URL, true, httpParam);

  }

  addModule(body) {
    return this.http.post<ServiceResponseBase<string>>(Constants.CBK_ADD_MODULE_URL, body, true);
  }

  getSubModules(value: string) {

    const httpParam = new HttpParams().append('moduleId', value);
    return this.http.get<ServiceResponseBase<Array<CustomIdFullName>>>(Constants.CBK_SUBMODULES_LIST_URL, true, httpParam);

  }

  addSubModule(body) {
    return this.http.post<ServiceResponseBase<string>>(Constants.CBK_ADD_SUBMODULE_URL, body, true);
  }

  addSubModuleApp(body: SubModuleApp) {
    return this.http.post<ServiceResponseBase<string>>(Constants.CBK_ADD_SUBMODULESAPP_URL, body, true);
  }


  getModulesView(body: ModuleView, page: number, pageSize: number) {

    const httpParam = new HttpParams().append('page', page.toString()).append('pageSize', pageSize.toString());
    return this.http.post<ServiceResponseBase<Page<ModuleView>>>(Constants.CBK_MODULES_VIEW_LIST_URL, body, true, httpParam);

  }

  deleteApplication(id:string){
    const httpParam = new HttpParams().append('applicationId',id);
    return this.http.delete<ServiceResponseBase<string>>(Constants.CBK_MODULES_DELETE_APP_URL, true, httpParam);

  }

}
