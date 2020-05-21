import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { MatPaginator, MatSort, MatTableDataSource, MatDialog, MatDialogConfig } from '@angular/material';
import { HttpParams } from '@angular/common/http';
import { ServiceResponseBase } from 'src/app/interface/service-response-base-interface';
import { Page } from 'src/app/interface/Paging/page.interface';
import { User } from 'src/app/interface/user.interface';
import { Constants } from 'src/environments/constants';
import { HttpRequestService } from 'src/app/service/http-request/http-request.service';
import { StateService } from 'src/app/service/state-service/state.service';
import { DialogApplContentComponent } from 'src/app/components/dialog-appl-content/dialog-appl-content.component';
import { distinctUntilChanged, debounceTime } from 'rxjs/operators';
import { DialogService } from 'src/app/components/dialog-alert/dialog.service';
import { ToastrService } from 'ngx-toastr';
import { CustomIdFullName } from 'src/app/interface/custom-id-full-name.interface';
import { Subscription } from 'rxjs';
import { ModulesRequestService } from 'src/app/service/modules-request/modules-request.service';
import { DatePipe } from '@angular/common';
import { ApplBody } from 'src/app/interface/appl-body-interface';

@Component({
  selector: 'app-appl-screen',
  templateUrl: './appl-screen.component.html',
  styleUrls: ['./appl-screen.component.css']
})
export class ApplScreenComponent implements OnInit {

  length: number;
  applForm: FormGroup;

  dataSource;
  dataTableIsLoading: boolean;
  loading: boolean;
  deliveryEditID: string;
  displayedColumns = ['content', 'applName', 'source', 'date', 'subModule', 'error'];

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;

  @ViewChild(MatSort, { static: false }) sort: MatSort;
  appls = [];
  subscription: Subscription;
  isLoading: boolean;
  subModulesArray: any[];
  modulesArray: any;
  trigger: number;



  constructor(
    private http: HttpRequestService, private state: StateService,
    private dialog: MatDialog, private alertDialog: DialogService, private toastr: ToastrService
    , private modulesRequestService: ModulesRequestService, private datepipe: DatePipe) { }

  ngOnInit() {
    this.initForm();

    const applName = this.state.data;

    if (applName) {

      this.getAppls(true, applName);
      this.state.data = null;
    }
    this.autoCompleteOnValueChange('moduleId');
  }

  autoCompleteOnValueChange(formControlName) {
    this.subscription = this.applForm.get(formControlName).valueChanges.pipe(
      debounceTime(300),
      // make sure if the value is differente from the old one
      distinctUntilChanged()
    ).subscribe(changedValue => {
      this.isLoading = true;
      this.subModulesArray = [];
      const val = this.returnFullNameFieldIfExist(changedValue);



      this.modulesRequestService.getModules(val ? val.replace(/\s*$/, "") : val).then(data => {
        if (!data) {
          this.isLoading = false;
          return;
        }


        this.modulesArray = data.payLoad;
        this.isLoading = false;
        const module: CustomIdFullName = this.autoCompletePrediction(formControlName, this.modulesArray, val);

        this.applForm.get('subModuleId').setValue(' ');


        const val1 = this.returnIdFieldIfExist(module ? module : changedValue);
        this.modulesRequestService.getSubModules(val1).then(data => {
          if (!data) {
            this.isLoading = false;
            return;
          }
          this.subModulesArray = data.payLoad;
          this.isLoading = false;
        });
      });
      if (changedValue.fullName || changedValue.id) {
        return
      }
    });

  }
  returnIdFieldIfExist(object: CustomIdFullName): string {
    if (object) {
      if (object.id) {
        return object.id;
      } else {
        return object.fullName;
      }
    }
  }

  returnFullNameFieldIfExist(object: any): string {
    if (object) {
      if (object.fullName) {
        return object.fullName;
      } else {
        return object;
      }
    }
  }
  autoCompletePrediction(formControlName, array, val): CustomIdFullName {
    let tmp;
    array.forEach(x => {
      if (x.fullName.replace(/\s*$/, "").toLowerCase() === val.replace(/\s*$/, "").toLowerCase()) {

        this.subscription.unsubscribe();
        // this.deliveryForm.patchValue({ [formControlName]: x });
        this.applForm.get(formControlName).setValue(x);

        this.trigger = Math.random();

        if (this.subscription.closed)
          this.autoCompleteOnValueChange(formControlName);
        tmp = x;
      }
    })
    if (tmp) return tmp;
    return null;
  }

  openAppl(errorMessage) {

    this.alertDialog.confirm('Errors', errorMessage, 'OK', 'null', 'lg');
  }

  update(id) {

    this.dataTableIsLoading = true;
    let user: User = JSON.parse(localStorage.getItem('userData'));

    let httpParam: HttpParams = new HttpParams().append('applId', id).append('updatedBy', user.id);

    this.http.get<any>(Constants.CBK_APPLURL_UPDATE, true, httpParam).then(data => {
      if ((data.error)) {
        this.alertDialog.confirm('Appl validation Error', data.error, 'OK', 'null', 'lg');
        this.dataTableIsLoading = false;

        return;
      }

      this.toastr.success('Successfully ' + 'Updated');
      this.dataTableIsLoading = false;

      if (data.message) {
        this.alertDialog.confirm('Appl Warnings', data.message, 'OK', 'null', 'lg');
      }
      this.search();
    }).catch(error => {
      this.dataTableIsLoading = false;
    });

  }
  search() {
    this.dataTableIsLoading = true;


const moduleId= this.returnIdFieldIfExist(this.applForm.get('moduleId').value)
const subModuleId = this.returnIdFieldIfExist(this.applForm.get('subModuleId').value)
const errorFlag = this.applForm.get('errorFlag').value ==true? 'O':null;

    const body = new ApplBody(this.applForm.get('applName').value ?this.applForm.get('applName').value:null, this.applForm.get('applContent').value,moduleId ? moduleId:null,
      subModuleId?subModuleId:null, this.applForm.get('fromDateInput').value,
      this.applForm.get('toDateInput').value,errorFlag );

    
      console.log(body)
    this.http.post<ServiceResponseBase<Array<any>>>(Constants.CBK_APPLS_BY_NAME_URL,body, true).then(data => {

      console.log(data)
      if(!data.payLoad){
      this.dataTableIsLoading = false;
      
      return;}
      this.appls = data.payLoad;
      // mat-table init
      this.dataSource = new MatTableDataSource(this.appls);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort

      this.dataTableIsLoading = false;
    }).catch(error => {
      this.dataTableIsLoading = false;
      this.appls = [];
      this.dataSource = new MatTableDataSource(this.appls);
      this.length = 0;
      return;
    });

  }

  initForm() {
    this.applForm = new FormGroup({
      applName: new FormControl(null),
      moduleId: new FormControl(null, [this.objectIsValid]),
      subModuleId: new FormControl(null),
      errorFlag: new FormControl(null),
      fromDateInput: new FormControl(null),
      toDateInput: new FormControl(null),
      applContent : new FormControl(null)
    });
  }
  objectIsValid(control: FormControl) {

    const object = control.value;
    if (!object) {
      return null;
    }

    if (!object.fullName && !object.id) {
      return {
        objectValidator: {
          valid: false
        }
      };
    }
  }


  getAppls(firstRequest: boolean, value: string) {
    this.dataTableIsLoading = true;
    let httpParam = new HttpParams();
    httpParam = httpParam.append('applName', value);
    this.http.get<ServiceResponseBase<Array<any>>>(Constants.CBK_APPLS_BY_NAME_In_URL, true, httpParam).then(data => {

      if (!data.payLoad) {
        this.dataTableIsLoading = false;
        this.appls = [];
        this.dataSource = new MatTableDataSource(this.appls);
        this.length = 0;
        return;
      }



      this.appls = data.payLoad;

      // mat-table init
      this.dataSource = new MatTableDataSource(this.appls);



      this.dataSource.paginator = this.paginator;
      // this.paginator.pageIndex=page;
      // this.dataSource.paginator.pageIndex=page;
      // sorting date

      this.dataSource.sort = this.sort;

      this.dataTableIsLoading = false;
    }).catch(error => {
      this.dataTableIsLoading = false;
      this.appls = [];
      this.dataSource = new MatTableDataSource(this.appls);
      this.length = 0;
      return;
    });
  }

  openContentDialog(element) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.height = 'auto';
    dialogConfig.width = '1200px';
    dialogConfig.data = element.id;

    this.dialog.open(DialogApplContentComponent, dialogConfig);

  }
}
