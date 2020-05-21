import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, Form, FormControl, Validators } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { HttpRequestService } from 'src/app/service/http-request/http-request.service';
import { HttpParams } from '@angular/common/http';
import { ServiceResponseBase } from 'src/app/interface/service-response-base-interface';
import { CustomIdFullName } from 'src/app/interface/custom-id-full-name.interface';
import { Constants } from 'src/environments/constants';
import { User } from 'src/app/interface/user.interface';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {

  userForm: FormGroup;

  loadder: boolean;
  usersArray: Array<CustomIdFullName>;
  roles: Array<CustomIdFullName>;

  consultant: boolean;
  statusCode: boolean = false;

  @Input() formType: string;
  @Input() user: User;

  constructor(private http: HttpRequestService, private toastr: ToastrService, private router: Router) { }

  ngOnInit() {
    this.initForm();
    this.getRoles();


    this.consultant = this.user ? this.user.consultant_flag === 'O' ? true : false : false;
    this.statusCode = this.user ? this.user.status_code === 'D' ? true : false : false;
  }

  initForm() {

    const role = this.user ? new CustomIdFullName(this.user.role.id, this.user.role.description) : null;

    this.userForm = new FormGroup({
      fullName: new FormControl(this.user ? this.user.fullName : null, Validators.required),
      username: new FormControl(this.user ? this.user.username : null, [Validators.required,
      Validators.pattern(/^[a-z0-9]+([_.-]?[a-z0-9])*$/)]),
      role: new FormControl(role ? role : null, [Validators.required, this.objectIsValid])
    });
  }

  onSubmit(userForm: FormGroup) {
    this.loadder = true;
    const role = this.returnIdFieldIfExist(userForm.get('role').value);

    const body = new User(this.user ? this.user.id : null, userForm.get('fullName').value, userForm.get('username').value,
      null, null, null, this.consultant ? 'O' : 'N', this.statusCode ? 'D' : 'A');
    const httpParam = new HttpParams().append('roleId', role);


    this.http.post<ServiceResponseBase<string>>(Constants.CBK_INSERT_USER_URL, body, true, httpParam).then(data => {

      if (!data) {

        this.loadder = false;
        return;
      }

      this.toastr.success('Successfully ' + this.formType + 'ed');
      this.router.navigate(['/users-page']);


    }).catch(error => {
      this.loadder = false;

    });

  }

  getRoles() {
    this.http.get<ServiceResponseBase<Array<CustomIdFullName>>>(Constants.CBK_GET_USER_ROLES_URL, true).then(data => {
      this.roles = data.payLoad;
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
