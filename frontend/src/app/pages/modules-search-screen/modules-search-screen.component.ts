import { Component, OnInit, ViewChild } from '@angular/core';
import { CustomIdFullName } from 'src/app/interface/custom-id-full-name.interface';
import { distinctUntilChanged, debounceTime } from 'rxjs/operators';
import { FormControl, FormGroup } from '@angular/forms';
import { RolesService } from 'src/app/service/roles-service/roles.service';
import { ModulesRequestService } from 'src/app/service/modules-request/modules-request.service';
import { ToastrService } from 'ngx-toastr';
import { Constants } from 'src/environments/constants';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { ModuleView } from 'src/app/interface/moduleView.interface';
import { trigger, transition, style, animate } from '@angular/animations';
import { StateService } from 'src/app/service/state-service/state.service';
import { Router } from '@angular/router';
import { timingSafeEqual } from 'crypto';
import { VirtualTimeScheduler, Subscription } from 'rxjs';

@Component({
  selector: 'app-modules-search-screen',
  templateUrl: './modules-search-screen.component.html',
  styleUrls: ['./modules-search-screen.component.css'],
  animations: [
    trigger(
      'inOutAnimation',
      [
        transition(
          ':enter',
          [
            style({ height: 0, opacity: 0 }),
            animate('0.4s ease-out',
              style({ height: 330, opacity: 1 }))
          ]
        ),
        transition(
          ':leave',
          [
            style({ height: 330, opacity: 1 }),
            animate('0.4s ease-in',
              style({ height: 0, opacity: 0 }))
          ]
        )
      ]
    )
  ]
})
export class ModulesSearchScreenComponent implements OnInit {

  moduleForm: FormGroup;

  loader: boolean;
  userRole: string;
  isLoading: boolean;
  modules = [];


  modulesArray: CustomIdFullName[];
  subModulesArray: CustomIdFullName[];

  displayedColumns = ['edit','subModuleName', 'appName', 'appl','moduleAdf', 'viewAdf',   'javaObj', 'javaObjVer'];

  dataSource;
  dataTableIsLoading: boolean;
  showInputSection: boolean;
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;

  @ViewChild(MatSort, { static: false }) sort: MatSort;
  length: number;
  body: ModuleView;
  isConsultant: boolean;
  trigger: number;
  subscription: Subscription;
  constructor(
    private roleService: RolesService, private modulesRequestService: ModulesRequestService, private toaster: ToastrService,
    private state: StateService, private router: Router) {

  }


  async ngOnInit() {

    let body = JSON.parse(localStorage.getItem('moduleBody'));
    if (body) {
      this.body = body;
      const paginator = this.state.paginator;
      this.getModules(this.body, paginator ? paginator.pageIndex :0, paginator? paginator.pageSize: 5, true)
      localStorage.removeItem('moduleBody');
    }

    Constants.userData = JSON.parse(localStorage.getItem('userData'));
    this.isConsultant = Constants.userData.consultant_flag === 'O' ? true : false;
    this.initForm();
    this.showInputSection = true;
    Constants.userData = JSON.parse(localStorage.getItem('userData'));

    await this.roleService.retrieveRoleCode(Constants.userData).then(role => {
      this.userRole = role;
    });
    
    this.autoCompleteOnValueChange('moduleId');

  }

  initForm() {

    this.moduleForm = new FormGroup({
      applicationName: new FormControl(this.body ? this.body.applicationName : null),
      modelAdfLib: new FormControl(),
      viewAdfLib: new FormControl(),
      moduleId: new FormControl(null, [this.objectIsValid]),
      subModuleId: new FormControl(),
      appl: new FormControl(),
      javaObj: new FormControl(),
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

  getModules(body, page: number, pageSize: number, firstRequest: boolean) {


    this.modulesRequestService.getModulesView(body, page, pageSize).then(data => {
      // this.dataSource = data;
      if (!data.payLoad) {
        this.loader = false;
        this.dataTableIsLoading = false;
        this.modules = [];
        this.dataSource = new MatTableDataSource(this.modules);
        this.length = 0;
        return;
      }



      this.modules = data.payLoad.content;
      this.length = data.payLoad.totalElements;

      // this.modules = data.payLoad;

      // mat-table init
      this.dataSource = new MatTableDataSource(this.modules);

      if (firstRequest) {

        this.dataSource.paginator = this.paginator;
        // sorting date

      }
      this.dataSource.sort = this.sort;
      this.dataTableIsLoading = false;
      this.loader = false;
    }).catch(error => {
      this.dataTableIsLoading = false;
      this.modules = [];
      this.dataSource = new MatTableDataSource(this.modules);
      this.length = 0;
      return;
    });
  }

  paginatorEvent(event) {
    if (this.body) {
      this.getModules(this.body, event.pageIndex, event.pageSize, false);
    }
  }


  autoCompleteOnValueChange(formControlName) {
    this.subscription=this.moduleForm.get(formControlName).valueChanges.pipe(
      debounceTime(300),
      // make sure if the value is differente from the old one
      distinctUntilChanged()
    ).subscribe(changedValue => {
      this.isLoading = true;
      this.subModulesArray = [];
      const val = this.returnFullNameFieldIfExist(changedValue);



        this.modulesRequestService.getModules(val?val.replace(/\s*$/, ""):val).then(data => {
        if (!data) {
          this.isLoading = false;
          return;
        }
        

        this.modulesArray = data.payLoad;
        this.isLoading = false;
       const module :CustomIdFullName= this.autoCompletePrediction(formControlName, this.modulesArray, val);

        this.moduleForm.get('subModuleId').setValue(' ');
        

        const val1 = this.returnIdFieldIfExist(module?module:changedValue);
        this.modulesRequestService.getSubModules(val1).then(data => {
          if (!data) {
            this.isLoading = false;
            return;
          }
          this.subModulesArray = data.payLoad;
          this.isLoading = false;
        });
      });
      if(changedValue.fullName || changedValue.id){
        return
      }
    });

  }
  autoCompletePrediction(formControlName, array, val) :CustomIdFullName{
    let tmp;
    array.forEach(x => {
      if (x.fullName.replace(/\s*$/, "").toLowerCase() === val.replace(/\s*$/, "").toLowerCase()) {

        this.subscription.unsubscribe();
        // this.deliveryForm.patchValue({ [formControlName]: x });
        this.moduleForm.get(formControlName).setValue(x);

        this.trigger = Math.random();
              
        if (this.subscription.closed)
        this.autoCompleteOnValueChange(formControlName);
      tmp=x;
      }
    })
    if(tmp)return tmp;
    return null;
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

  onSubmit(moduleForm) {
const moduleId= this.returnIdFieldIfExist(moduleForm.get('moduleId').value)? this.returnIdFieldIfExist(moduleForm.get('moduleId').value):null
    this.body = new ModuleView(null, moduleForm.get('applicationName').value, moduleForm.get('modelAdfLib').value,
      moduleForm.get('viewAdfLib').value,this.returnFullNameFieldIfExist(moduleForm.get('subModuleId').value),
      moduleForm.get('appl').value, moduleForm.get('javaObj').value, null,
      moduleId,
      null, null);

    this.loader = true;
    this.getModules(this.body, 0, 5, false);


  }


  openEditForm(element) {
    this.state.data = element;
    this.state.paginator = this.paginator;
    this.router.navigate(['/module-edit-screen'])

    localStorage.setItem('moduleBody', JSON.stringify(this.body));

  }

  showHideInputSection() {
    this.showInputSection = !this.showInputSection;
  }


}
