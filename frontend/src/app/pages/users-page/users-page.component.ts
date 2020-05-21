import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { HttpRequestService } from 'src/app/service/http-request/http-request.service';
import { Constants } from 'src/environments/constants';
import { ServiceResponseBase } from 'src/app/interface/service-response-base-interface';
import { User } from 'src/app/interface/user.interface';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { CustomIdFullName } from 'src/app/interface/custom-id-full-name.interface';
import { StateService } from 'src/app/service/state-service/state.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth-Service/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-users-page',
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.css']
})
export class UsersPageComponent implements OnInit {
  users: Array<User> = [];
  usersArray: Array<CustomIdFullName> = [];
  length: number;
  usersForm: FormGroup;

  check:boolean;

  dataSource;
  dataTableIsLoading: boolean;
  loading: boolean;
  deliveryEditID: string;
  displayedColumns = ['edit', 'fullName', 'username', 'role', 'consultant', 'statusCode'];

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;

  @ViewChild(MatSort, { static: false }) sort: MatSort;

  constructor(private http: HttpRequestService, private state: StateService, private router: Router, private authService: AuthService, private toastr: ToastrService) { }

  ngOnInit() {
    this.initForm();
    // make sure if the value is differente from the old one



    this.getUsers();




    // this.getUsers(true);
  }

  initForm() {
    this.usersForm = new FormGroup({
      userFullName: new FormControl()
    });
  }

  getUsers() {
    this.dataTableIsLoading = true;
    this.http.get<ServiceResponseBase<Array<User>>>(Constants.CBK_GET_USER_URL, true).then(data => {


      if (!data.payLoad) {
        this.dataTableIsLoading = false;
        this.users = [];
        this.dataSource = new MatTableDataSource(this.users);
        this.length = 0;
        return;
      }


      this.users = data.payLoad;



      // mat-table init
      this.dataSource = new MatTableDataSource(this.users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort


      // test

      // if (firstRequest) {

      //   this.dataSource.paginator = this.paginator;
      //   // this.paginator.pageIndex=page;
      //   // this.dataSource.paginator.pageIndex=page; 
      //   // sorting date

      //   this.dataSource.sort = this.sort;
      // }
      this.dataTableIsLoading = false;
    }).catch(error => {
      this.dataTableIsLoading = false;
      this.users = [];
      this.dataSource = new MatTableDataSource(this.users);
      this.length = 0;
      return;
    });
  }

  doFilter = (value: string) => {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
  }
  openEditForm(user: User) {
    this.state.data = user;
    this.router.navigate(['/users-edit-page']);
  }

  resetPassword(element) {
    this.authService.requestResetPassword(element.username)

    this.toastr.success('An email has been sent to ' + element.fullName + ' to change password', 'Reset Password');
  }

  doFilterConsultant(check, bool) {

    
    if (check) {
      let users = this.users.filter(x => {
        if (bool) {

          if (x.consultant_flag === 'O')
            return x;
        } else {
          if (x.consultant_flag === 'N')
            return x;
        }
      });
      this.dataSource = new MatTableDataSource(users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;

    } else {
      this.dataSource = new MatTableDataSource(this.users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }

  }

  doFilterdDisabled(check) {
    if (check) {
      let users = this.users.filter(x => {
        if (x.status_code === 'D')
          return x;
      });
      this.dataSource = new MatTableDataSource(users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;

    } else {
      this.dataSource = new MatTableDataSource(this.users);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }

  }

}
