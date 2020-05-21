import { Component, OnInit, OnDestroy } from '@angular/core';

import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth-Service/auth.service';
import { AlertService } from '../../service/alert/alert.service';
import { MatDialog } from '@angular/material';
import { DialogRequestResetPasswordComponent } from 'src/app/components/dialog-request-reset-password/dialog-request-reset-password.component';
@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
  providers: [AlertService]
})
export class LoginPageComponent implements OnInit {
  error: string;
  loggingIn:boolean;

  constructor(private authService: AuthService, private router: Router, private alertService: AlertService, private dialog: MatDialog) { }

  ngOnInit() {
  }
  login(form: NgForm) {
    this.loggingIn=true;
    this.alertService.clear();
    const username: string = form.value.username;
    const password: string = form.value.password;

    this.authService.login(username, password).then(response => {

      if (response == null) {

        return;
      }

      this.router.navigate(['/home']);


      this.loggingIn=false;
      form.reset();
    }).catch(error=>{
      this.loggingIn=false;
      form.reset();

    });

  }
  openResetPasswordDialog() {
    this.dialog.open(DialogRequestResetPasswordComponent);
  }
}
