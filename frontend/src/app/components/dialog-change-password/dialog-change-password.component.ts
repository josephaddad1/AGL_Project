import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/service/auth-Service/auth.service';
import { Router } from '@angular/router';
import { MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-dialog-change-password',
  templateUrl: './dialog-change-password.component.html',
  styleUrls: ['./dialog-change-password.component.css']
})
export class DialogChangePasswordComponent implements OnInit {

  constructor(private authService: AuthService, private toastr: ToastrService, private router: Router, public dialogRef: MatDialogRef<DialogChangePasswordComponent>) { }

  ngOnInit() {
  }


  changePassword(changeForm) {
    if (changeForm.value.newPassword !== changeForm.value.confPassword) {

      this.toastr.error('Password does not match');
      changeForm.reset();
      return;
    }
    this.authService.changePassword(changeForm.value.oldPassword, changeForm.value.newPassword, changeForm.value.confPassword).then(data => {
      if (data) {
        this.toastr.success('Password Successfully changed');
        this.dialogRef.close();
        this.authService.logout();
      }
    }).catch(error => {
      changeForm.reset();
    });


  }

}
