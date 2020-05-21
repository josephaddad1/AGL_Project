import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { RolesService } from 'src/app/service/roles-service/roles.service';
import { User } from 'src/app/interface/user.interface';
import { ModulesRequestService } from 'src/app/service/modules-request/modules-request.service';
import { CustomIdFullName } from 'src/app/interface/custom-id-full-name.interface';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { SubModuleApp } from 'src/app/interface/SubModuleApp.interface';
import { ToastrService } from 'ngx-toastr';
import { Constants } from 'src/environments/constants';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { DialogAddModuleComponent } from '../dialog-add-module/dialog-add-module.component';
import { DialogAddSubModuleComponent } from '../dialog-add-sub-module/dialog-add-sub-module.component';
import { DialogService } from '../dialog-alert/dialog.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-modules-form',
  templateUrl: './modules-form.component.html',
  styleUrls: ['./modules-form.component.css']
})
export class ModulesFormComponent implements OnInit {


  moduleForm: FormGroup;

  loader: boolean;
  userRole: string;
  isLoading: boolean;

  @Input() formType;
  @Input() module;
  modulesArray: CustomIdFullName[];
  subModulesArray: CustomIdFullName[];
  constructor(
    private dialog: MatDialog, private roleService: RolesService, private modulesRequestService: ModulesRequestService,
    private toaster: ToastrService, private alertDialog: DialogService, private toastr: ToastrService,private router :Router) {

  }

  @Output() formToParent = new EventEmitter<FormGroup>();
  @Output() isSubmittedToParent = new EventEmitter<boolean>();

  async ngOnInit() {
    this.initForm();
    Constants.userData = JSON.parse(localStorage.getItem('userData'));

    await this.roleService.retrieveRoleCode(Constants.userData).then(role => {
      this.userRole = role;
    });
    this.autoCompleteOnValueChange('module');

    this.moduleForm.get('module').valueChanges.pipe(

      debounceTime(300),
      // make sure if the value is differente from the old one
      distinctUntilChanged()
    ).subscribe(changedValue => {

      this.isLoading = true;


      // NOT SELECTING FROM DROPDOWN
      if (!changedValue.id) {
        return;
      }

      this.moduleForm.get('subModule').setValue(' ');

      const val = this.returnIdFieldIfExist(changedValue);
      this.modulesRequestService.getSubModules(val).then(data => {
        if (!data) {
          this.isLoading = false;
          return;
        }
        this.subModulesArray = data.payLoad;
        this.isLoading = false;
      });
    });

    this.formToParent.emit(this.moduleForm);


  }

  initForm() {
    this.moduleForm = new FormGroup({
      applicationName: new FormControl(this.module ? this.module.applicationName : null, [Validators.required]),
      modelAdfLib: new FormControl(this.module ? this.module.modelAdfLib : null),
      viewAdfLib: new FormControl(this.module ? this.module.viewAdfLib : null),
      module: new FormControl(this.module ? new CustomIdFullName(this.module.moduleId, this.module.moduleName) : null,
        [Validators.required, this.objectIsValid]),
      subModule: new FormControl(this.module ? new CustomIdFullName(this.module.subModuleId, this.module.subModuleName) : null,
        [Validators.required, this.objectIsValid]),
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

  delete() {
    this.alertDialog.confirm('Attention!!', 'Do you really want to delete this application?').then(data => {

      if (data) {
        this.loader = true;
        this.modulesRequestService.deleteApplication(this.module.id).then(data => {

          this.toastr.success(data.payLoad);
          this.router.navigate(['/module-search-screen']);

          this.loader = false;


        }).catch(error => {
          this.toastr.error(error);

          this.loader = false;


        });
      }
    });
  }


  autoCompleteOnValueChange(formControlName) {
    this.moduleForm.get(formControlName).valueChanges.pipe(
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
      });
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

  onSubmit(moduleForm) {
    this.isSubmittedToParent.emit(false);
    this.loader = true;

    const moduleAdf = moduleForm.get('modelAdfLib').value;
    const viewAdf = moduleForm.get('viewAdfLib').value;


    const body = new SubModuleApp(this.module ? this.module.id : null,
      moduleForm.get('applicationName').value, moduleAdf, viewAdf, moduleForm.get('subModule').value.id);

    this.modulesRequestService.addSubModuleApp(body).then(data => {

      this.toaster.success(data.payLoad);
      this.isSubmittedToParent.emit(true);
      this.router.navigate(['/module-search-screen']);

      this.loader = false;
      this.moduleForm.reset();

    });


  }
  openModuleDialog() {
    this.dialog.open(DialogAddModuleComponent);
  }

  openSubModuleDialog() {

    const dialogConfig = new MatDialogConfig();
    dialogConfig.height = 'auto';
    // dialogConfig.maxHeight = '600px';
    dialogConfig.width = '800px';
    dialogConfig.data = this.moduleForm.get('module').value;
    dialogConfig.panelClass = 'custom-modalbox'

    this.dialog.open(DialogAddSubModuleComponent, dialogConfig);
  }
}
