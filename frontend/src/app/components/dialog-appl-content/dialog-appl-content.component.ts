import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { HttpRequestService } from 'src/app/service/http-request/http-request.service';
import { ServiceResponseBase } from 'src/app/interface/service-response-base-interface';
import { Constants } from 'src/environments/constants';
import { HttpParams } from '@angular/common/http';
import { MatTableDataSource, MAT_DIALOG_DATA, MatDialogRef, MatSort } from '@angular/material';

@Component({
  selector: 'app-dialog-appl-content',
  templateUrl: './dialog-appl-content.component.html',
  styleUrls: ['./dialog-appl-content.component.css']
})
export class DialogApplContentComponent implements OnInit {


  dataSource;
  dataTableIsLoading: boolean;
  loading: boolean;
  deliveryEditID: string;
  applId: string;
  displayedColumns = ['objectName', 'objectType', 'version'];

  @ViewChild(MatSort, { static: false }) sort: MatSort;
  content = [];
  constructor(
    private http: HttpRequestService, private dialogRef: MatDialogRef<DialogApplContentComponent>, @Inject(MAT_DIALOG_DATA) data) {
    this.applId = data;

  }

  ngOnInit() {

    
   this.search(this.applId);

  }
  cancel() {
    this.dialogRef.close()
  }
  

  search(id:string,parentId?){
    this.dataTableIsLoading = true;
    let httpParam;
    let url;
    if(id){
      httpParam= new HttpParams().append('applId', id);
    url=Constants.CBK_APPL_CONTENT_URL;
    }else{
     httpParam = new HttpParams().append('parentId', parentId);
     url=Constants.CBK_APPL_CONTENT_BY_PARENT_ID_URL;

    }
    this.http.get<ServiceResponseBase<Array<any>>>(url, true, httpParam).then(data => {
   
    
      if (!data.payLoad) {
        this.dataTableIsLoading = false;
        this.content = [];
        this.dataSource = new MatTableDataSource(this.content);
        return;
      }

      this.content = data.payLoad;


      // mat-table init
      this.dataSource = new MatTableDataSource(this.content);

      this.dataSource.sortingDataAccessor = (item, property): string | number => {
        
        switch (property) {
          case 'objectName': {
            if(item.objectName.endsWith('.appl'))
        
            return item;
          }
          default: return item[property];
        }
      };

      this.dataSource.sort= this.sort
      this.dataTableIsLoading = false;
    }).catch(error => {
      this.dataTableIsLoading = false;
      this.content = [];
      this.dataSource = new MatTableDataSource(this.content);
      return;


    });
  }


  
}
