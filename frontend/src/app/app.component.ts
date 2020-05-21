import { Component, OnInit, OnDestroy, HostListener } from '@angular/core';
import { AuthService } from './service/auth-Service/auth.service';
import { UserIdleService } from 'angular-user-idle';
import { AlertService } from './service/alert/alert.service';
import { Subscription } from 'rxjs';
import { DialogService } from './components/dialog-alert/dialog.service';

import { ToastrService } from 'ngx-toastr';
import { Constants } from 'src/environments/constants';
import { User } from './interface/user.interface';
import { RolesService } from './service/roles-service/roles.service';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
  ,
  providers: [AlertService]
})
export class AppComponent implements OnInit, OnDestroy {


  isLoggedIn = false;
  subcription: Subscription;
  expired = false;
  isConsultant: boolean;
  userRole: string;
  isAdmin: boolean;


  constructor(
    private authService: AuthService, private userIdle: UserIdleService,
    private alertService: AlertService, private toastr: ToastrService,private dialogRef: MatDialog) {

  }

  async ngOnInit() {


    this.authService.autoLogin();
    
    this.subcription = Constants.user.subscribe(user => {
      if (user != null) {
        this.isLoggedIn = true;
      } else {
        this.isLoggedIn = false;
        return;
      }


      this.isConsultant = user.consultant_flag === 'O' ? true : false;
    });
    Constants.isAdmin.subscribe(admin=>{
      this.isAdmin=admin;
     });


    this.userIdle.startWatching();

    // Start watching when user idle is starting.
    this.userIdle.onTimerStart().subscribe(count => {


      this.userIdle.resetTimer();
    });

    // Start watch when time is up.
    this.userIdle.onTimeout().subscribe(() => {

      this.isLoggedIn = false;
      this.expired = true;

      this.authService.logout();
      this.toastr.warning('Session expired!');
    });

    // setting new date
    localStorage.setItem('logInDate', JSON.stringify(new Date()));

  }

  ngOnDestroy() {
    this.subcription.unsubscribe();
  }


  logout() {
    this.authService.logout();
  }


}



