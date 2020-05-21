
import { HttpErrorResponse } from '@angular/common/http';
import { throwError, Observable, of } from 'rxjs';
import { Constants } from '../../../environments/constants';
import { ToastrService } from 'ngx-toastr';
import { Injectable, ViewContainerRef } from '@angular/core';
import { NewTokenService } from '../new-token/new-token.service';
import { AuthService } from '../auth-Service/auth.service';
import { Router } from '@angular/router';
import { DialogService } from 'src/app/components/dialog-alert/dialog.service';
@Injectable()
export class ErrorHandelerService {


  constructor(private toastr: ToastrService, private router: Router, private dialog: DialogService) {

  }


  handleError = (errorRes: HttpErrorResponse) => {


    let errorMessage = Constants.CBK_UKE_01;
    if (!errorRes.error || !errorRes.error.error) {
      if ((errorRes.message).includes('Http failure response')) {
        this.toastr.error('Response failure', 'Server Error');
        return throwError('Response failure');
      }
      this.toastr.error(errorMessage, 'Server Error');
      return throwError(errorMessage);
    }

    switch (errorRes.error.message) {
      case 'CBK-AUTH-001':
        errorMessage = Constants.CBK_AUTH_001;
        break;
      case 'CBK-AUTH-002':
        errorMessage = Constants.CBK_AUTH_002;
        break;
      case 'CBK-AUTH-003':
        errorMessage = Constants.CBK_AUTH_003;
        break;

      case 'CBK-AUTH-004': {
        errorMessage = Constants.CBK_AUTH_004;
        Constants.user.next(null);
        localStorage.clear();
        this.router.navigate(['/login']);
        this.toastr.warning(errorMessage);

        break;
      }

      case 'CBK-AUTH-005':
        errorMessage = Constants.CBK_AUTH_005;
        break;

      case 'CBK-AUTH-006':
        errorMessage = Constants.CBK_AUTH_006;
        break;

      case 'CBK-AUTH-007':
        errorMessage = Constants.CBK_AUTH_007;
        break;

      case 'CBK-AUTH-008':
        errorMessage = Constants.CBK_AUTH_008;
        break;
      default: errorMessage = errorRes.error.error;
    }

    // if (errorMessage.startsWith("java.lang.Exception: Appl-Error")) {
    //   errorMessage = errorMessage.slice(32);
    //   console.log(errorMessage)
    //   const array: Array<string> = errorMessage.split(': ');

    //   console.log(array)
    //   this.dialog.confirm("Appl error", null, array, 'OK', '', 'lg');
    //   return throwError(errorMessage);

    // }
    if (errorRes.error.message != "CBK-AUTH-004")
      this.toastr.error(errorMessage, 'Server Error');

    return throwError(errorMessage);
  }


}
