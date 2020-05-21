import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/service/auth-Service/auth.service';
import { MatDialogRef } from '@angular/material';
import { ToastrService } from 'ngx-toastr';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Constants } from 'src/environments/constants';

@Component({
  selector: 'app-dialog-request-reset-password',
  templateUrl: './dialog-request-reset-password.component.html',
  styleUrls: ['./dialog-request-reset-password.component.css']
})
export class DialogRequestResetPasswordComponent implements OnInit {

  constructor(private authService: AuthService, private dialogRef: MatDialogRef<DialogRequestResetPasswordComponent>
    ,  private http: HttpClient) { }

  ngOnInit() {
  }
  username;

  getResetUrl() {
   this.authService.requestResetPassword(this.username);

  }



  cancel() {
    this.dialogRef.close();
  }
}
