import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { ModulesRequestService } from 'src/app/service/modules-request/modules-request.service';
import { CustomIdFullName } from 'src/app/interface/custom-id-full-name.interface';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-dialog-add-sub-module',
  templateUrl: './dialog-add-sub-module.component.html',
  styleUrls: ['./dialog-add-sub-module.component.css']
})
export class DialogAddSubModuleComponent implements OnInit {

  subModuleForm: FormGroup;
  isLoading: boolean;
  modulesArray: CustomIdFullName[];

  module;
  constructor(
    private modulesRequestService: ModulesRequestService, private dialogRef: MatDialogRef<DialogAddSubModuleComponent>,
    private toast: ToastrService, private http: ModulesRequestService, @Inject(MAT_DIALOG_DATA) data) {
    this.module = data;
  }

  ngOnInit() {

    this.initForm();

    this.autoCompleteOnValueChange('module');
  }


  initForm() {
    this.subModuleForm = new FormGroup({
      subModuleName: new FormControl(null, [Validators.required]),
      appl: new FormControl(null, [Validators.required]),
      javaObj: new FormControl(null, [Validators.required]),
      javaObjVersion: new FormControl('1.0.0', [Validators.required, Validators.pattern(/^(\d+\.)?(\d+\.)?(\d+\.)?(\*|\d+)$/)]),
      module: new FormControl({ value: this.module ? this.module : null, disabled: true }, [Validators.required, this.objectIsValid]),
    });
  }





  autoCompleteOnValueChange(formControlName) {
    this.subModuleForm.get(formControlName).valueChanges.pipe(
      debounceTime(300),
      // make sure if the value is differente from the old one
      distinctUntilChanged()
    ).subscribe(changedValue => {
      this.isLoading = true;
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

  returnFullNameFieldIfExist(object: any): string {
    if (object) {
      if (object.fullName) {
        return object.fullName;
      } else {
        return object;
      }
    }
  }


  cancel() {
    this.subModuleForm.reset();
    this.dialogRef.close();
  }

  addSubModule(subModuleForm: FormGroup) {

    const body = {
      name: subModuleForm.get('subModuleName').value, appl: subModuleForm.get('appl').value, javaObj: subModuleForm.get('javaObj').value
      , javaObjVersion: subModuleForm.get('javaObjVersion').value, moduleId: subModuleForm.get('module').value.id
    };

    this.http.addSubModule(body).then(data => {
      this.toast.success(data.payLoad);
      this.dialogRef.close();
    }).catch(error => {
      this.toast.error(error);
      this.dialogRef.close();
    })
  }

  // disable button if not valid
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
}
