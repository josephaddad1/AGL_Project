import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { ToastrService } from 'ngx-toastr';
import { ModulesRequestService } from 'src/app/service/modules-request/modules-request.service';

@Component({
  selector: 'app-dialog-add-module',
  templateUrl: './dialog-add-module.component.html',
  styleUrls: ['./dialog-add-module.component.css']
})
export class DialogAddModuleComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<DialogAddModuleComponent>
    , private toast: ToastrService, private http: ModulesRequestService) { }
  name;
  parentAppl

  ngOnInit() {
  }
  addModule() {
    this.http.addModule({ id: null, name: this.name, parentApplName: this.parentAppl }).then(data => {

      this.toast.success(data.payLoad);
      this.dialogRef.close();
    }).catch(error => {
      this.toast.error(error);
      this.dialogRef.close();
    });
  }


  cancel() {
    this.dialogRef.close();
  }
}
