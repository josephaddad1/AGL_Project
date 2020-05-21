import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/service/auth-Service/auth.service';
import { AlertService } from 'src/app/service/alert/alert.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  private token: string;
  constructor(private authService: AuthService, private alertService: AlertService, private route: ActivatedRoute
    , private router: Router, private toastr: ToastrService) { }

  ngOnInit() {
    this.route.queryParams.subscribe(data => {
      this.token = data.expire;
    });

  }
  resetPassword(resetForm) {
    if (resetForm.value.newPassword !== resetForm.value.confPassword) {

      this.alertService.error('Password does not match')
      return;
    }
    this.authService.resetPassword(resetForm.value.newPassword, resetForm.value.confPassword, this.token).then(data => {
      if (data) {
        this.toastr.success('Password Successfully changed');
      }
    }).catch(error => {this.toastr.error(error)
    });

    this.router.navigate(['/login']);
  }
}
