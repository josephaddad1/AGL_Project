import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { RolesService } from 'src/app/service/roles-service/roles.service';
import { CustomIdFullName } from 'src/app/interface/custom-id-full-name.interface';
import { User } from 'src/app/interface/user.interface';
import { DialogChangePasswordComponent } from 'src/app/components/dialog-change-password/dialog-change-password.component';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { Constants } from 'src/environments/constants';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfilePageComponent implements OnInit {

  userForm: FormGroup;
  userRole: string;
  loggedUser: CustomIdFullName;
  isConsultant :boolean;
  constructor(private roleService: RolesService, private dialog: MatDialog) {


  }

  ngOnInit() {


    this.initForm();


  }

  onSubmit(userForm: FormGroup) {

  }

  async initForm() {


    Constants.userData = JSON.parse(localStorage.getItem('userData'));
    this.loggedUser = new CustomIdFullName(Constants.userData.id, Constants.userData.fullName);
    this.isConsultant = Constants.userData.consultant_flag === 'O' ? true : null;
    this.userForm = new FormGroup({
      name: new FormControl({ value: this.loggedUser, disabled: true }, Validators.required),
      username: new FormControl({ value: Constants.userData.username, disabled: true }),
      role: new FormControl({ value: null, disabled: true }, Validators.required),
      consultant: new FormControl({ value:this.isConsultant, disabled: true })

    });
    await this.roleService.retrieveRole(Constants.userData).then(role => this.userForm.patchValue({ 'role': role }));

  }

  changePassword() {
    const dialogConf = new MatDialogConfig();
    dialogConf.height = '70%';
    dialogConf.width = '40%';
    this.dialog.open(DialogChangePasswordComponent, dialogConf);
  }
}
