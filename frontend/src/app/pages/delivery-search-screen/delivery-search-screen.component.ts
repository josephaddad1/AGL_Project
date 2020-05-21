import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { DeliveryRequestService } from 'src/app/service/delivery-request/delivery-request.service';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { MatPaginator, MatTableDataSource, MatSort, MatDialog } from '@angular/material';
import { DeliveryView } from 'src/app/interface/DeliveryView.interface';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { RolesService } from 'src/app/service/roles-service/roles.service';
import { CustomIdFullName } from 'src/app/interface/custom-id-full-name.interface';
import { trigger, transition, style, animate } from '@angular/animations';
import { StateService } from 'src/app/service/state-service/state.service';
import { Delivery } from 'src/app/interface/delivery.interface';
import { DeliveryDetails } from 'src/app/interface/delivery-details.interface';
import { InsertBody } from 'src/app/interface/insert-body.interface';
import { DialogService } from 'src/app/components/dialog-alert/dialog.service';
import { Constants } from 'src/environments/constants';
import { HttpParams } from '@angular/common/http';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-delivery-search-screen',
  templateUrl: './delivery-search-screen.component.html',
  styleUrls: ['./delivery-search-screen.component.css'],
  providers: [],
  animations: [
    trigger(
      'inOutAnimation',
      [
        transition(
          ':enter',
          [
            style({ height: 0, opacity: 0 }),
            animate('0.6s ease-out',
              style({ height: 357, opacity: 1 }))
          ]
        ),
        transition(
          ':leave',
          [
            style({ height: 357, opacity: 1 }),
            animate('0.6s ease-in',
              style({ height: 0, opacity: 0 }))
          ]
        )
      ]
    )
  ]
})
export class DeliverySearchScreenComponent implements OnInit {
  length: any;
  deliveryForm: FormGroup;
  isLoading = false;
  dataTableIsLoading = false;
  deliverires: DeliveryView[] = [];
  deliveredByArray: Array<CustomIdFullName> = [];
  clientsArray: Array<CustomIdFullName> = [];
  sourceArray: Array<CustomIdFullName> = [];
  typesArray;
  loggedUser: CustomIdFullName;

  showInputSection = false;
  userRole: string;
  isConsultant: boolean;

  selectedRowIndex: string = '-1';

  body: DeliveryView;
  subscription: Subscription;
  trigger;
  // number of request for the table pagination
  counter: number;
  // table
  dataSource;
  deliveryEditID: string;
  displayedColumns = ['edit', 'error', 'type', 'functionality', 'client', 'source', 'ticket',
    'appl', 'deliveredBy', 'approved', 'comments', 'releaseDate', 'incApp'];

  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;

  @ViewChild(MatSort, { static: false }) sort: MatSort;





  constructor(
    private deliveryRequestService: DeliveryRequestService, private datepipe: DatePipe, private roleService: RolesService,
    private stateService: StateService, private router: Router, private toastr: ToastrService,
    private alertDialog: DialogService, ) { }




  async ngOnInit() {
    Constants.userData = JSON.parse(localStorage.getItem('userData'));
    this.isConsultant = Constants.userData.consultant_flag === 'O' ? true : false;
    this.loggedUser = new CustomIdFullName(Constants.userData.id, Constants.userData.fullName);

    const localStorageBody: DeliveryView = JSON.parse(localStorage.getItem('deliverySearchInput'));
    const pageIndex = localStorage.getItem('paginator-page-index');
    const paginatorPageSize = localStorage.getItem('paginator-page-size');


    // if (localStorageBody) {
    //   this.body = localStorageBody;
    // } else {

    // this.deliveryEditID=localStorage.getItem('deliveryId');
    let flag = false;
    if (localStorageBody) {
      this.body = localStorageBody;
      localStorage.removeItem('paginator-page-index');
      localStorage.removeItem('paginator-page-size');
      flag = true;
    } else {
      this.body = new DeliveryView(null, null, null, null, null, null, null, null, null, null, null, Constants.userData.fullName, null,
        null, null, null, null, null, null, null);
    }

    // }
    // this.getDeliveries(this.body, 0, 5, false);
    this.getDeliveries(this.body, pageIndex ? parseInt(pageIndex) : 0, paginatorPageSize ? parseInt(paginatorPageSize) : 5, true);


    this.counter = 0;
    this.initForm();

    await this.roleService.retrieveRoleCode(Constants.userData).then(role => {
      this.userRole = role;
    });

    this.deliveryRequestService.getTypes().then(data => {
      if (data) {
        this.typesArray = data.payLoad;
      }
    });
    this.autoCompleteOnValueChange('clientInput', 'client');
    this.autoCompleteOnValueChange('sourceInput', 'source');
    this.autoCompleteOnValueChange('deliveredByInput', 'deliveredBy');
  }



  initForm() {
    this.deliveryForm = new FormGroup({
      functionalityInput: new FormControl(null),
      deliveredByInput: new FormControl(this.loggedUser),
      applInput: new FormControl(null),
      sourceInput: new FormControl(null),
      ticketInput: new FormControl(null),
      clientInput: new FormControl(null),
      typeInput: new FormControl(null),
      releaseDateInput: new FormControl(null),
      toDateInput: new FormControl(null)
    });
  }
  // on value change of form input send request to server to get the lov(client, source, delivered by)
  autoCompleteOnValueChange(formControlName, input) {
    this.subscription = this.deliveryForm.get(formControlName).valueChanges.pipe(
      debounceTime(300),
      // make sure if the value is differente from the old one
      distinctUntilChanged()
    ).subscribe(changedValue => {
      this.isLoading = true;
      const val = this.returnFullNameFieldIfExist(changedValue);

      if(changedValue.fullName || changedValue.id){
        return
      }

      this.deliveryRequestService.getDeliveryInit(val ? val.replace(/\s*$/, "") : val, input).then(data => {
        if (!data) {
          this.isLoading = false;
          return;
        }
        if (input === 'source') {
          this.sourceArray = data.payLoad.source;
          this.autoCompletePrediction(formControlName, input, this.sourceArray, val);
        }
        else if (input === 'deliveredBy') {
          this.deliveredByArray = data.payLoad.deliveredBy;


          this.autoCompletePrediction(formControlName, input, this.deliveredByArray, val);


        } else if (input === 'client') {
          this.clientsArray = data.payLoad.clients;
          this.autoCompletePrediction(formControlName, input, this.clientsArray, val);
          
        }


        this.isLoading = false;
      });
    });

  }

  autoCompletePrediction(formControlName, input, array, val) {
    array.forEach(x => {
      if (x.fullName.replace(/\s*$/, "").toLowerCase() === val.replace(/\s*$/, "").toLowerCase()) {

        this.subscription.unsubscribe();
        // this.deliveryForm.patchValue({ [formControlName]: x });
        this.deliveryForm.get(formControlName).setValue(x);

        this.trigger = Math.random();
              
        if (this.subscription.closed)
        this.autoCompleteOnValueChange(formControlName, input);

      }

    })
  }
  showHideInputSection() {
    this.showInputSection = !this.showInputSection;
  }

  onSubmit(deliveryFormEvent: FormGroup) {
    let firstRequest;
    if (this.counter === 0) {
      firstRequest = true;
    } else {
      firstRequest = false;
    }
    this.counter += 1;

    let functionality = deliveryFormEvent.get('functionalityInput').value;
    if (!functionality) {
      functionality = null;
    }


    // if the value returned from autocomplete component is of type { id, fullName }
    // just send the name to the search request ( for delivered by, source, client, type).
    const deliveredBy = this.returnFullNameFieldIfExist(deliveryFormEvent.get('deliveredByInput').value);
    const source = this.returnFullNameFieldIfExist(deliveryFormEvent.get('sourceInput').value);
    const client = this.returnFullNameFieldIfExist(deliveryFormEvent.get('clientInput').value);
    const type = this.returnFullNameFieldIfExist(deliveryFormEvent.get('typeInput').value);





    this.body = new DeliveryView(null, functionality, null, type,
      null, client, null, source,
      deliveryFormEvent.get('ticketInput').value,
      deliveryFormEvent.get('applInput').value, null, deliveredBy,


      this.datepipe.transform(deliveryFormEvent.get('releaseDateInput').value, 'yyyy-MM-dd'),
      this.datepipe.transform(deliveryFormEvent.get('toDateInput').value, 'yyyy-MM-dd'),



      null, null, null, null, null, null);

    // first page by default is 0 and pageSize is 5

    this.getDeliveries(this.body, 0, this.paginator.pageSize, firstRequest);
  }

  //// date validation Example: 12-JAN-2019
  // dateValidator(control: FormControl) {

  //   const date = control.value;
  //   if (!date) {
  //     return null;
  //   }

  //   const dateRegEx = new RegExp(`^(?:|(([0-2][0-9]|(3)[0-1])(\-)(Jan|jan|JAN|FEB|feb|Feb|Mar|mar|
  //   MAR|apr|Apr|APR|May|may|MAY|Jun|jun|JUN|JUL|jul|Jul|Aug|aug|AUG|sep|Sep|SEP|Oct|oct|OCT
  //   |nov|Nov|NOV|dec|DEC|Dec)(\-)[0-9][0-9][0-9][0-9])|)$`);

  //   if (!dateRegEx.test(date)) {

  //     return {
  //       dateValidator: {
  //         valid: false
  //       }
  //     };
  //   }
  // }


  public doFilter = (value: string) => {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
  }

  async getDeliveries(body, page: number, pageSize: number, firstRequest: boolean) {
    this.dataTableIsLoading = true;
    await this.deliveryRequestService.getDeliveries(body, page, pageSize).then(data => {

      if (!data.payLoad) {
        this.dataTableIsLoading = false;
        return;
      }
      if (data.payLoad.empty) {
        this.dataTableIsLoading = false;
        this.deliverires = [];
        this.dataSource = new MatTableDataSource(this.deliverires);
        this.length = 0;
        return;
      }
      this.deliverires = data.payLoad.content;
      this.length = data.payLoad.totalElements;

      // changing date format
      this.deliverires.forEach(row =>
        row.releaseDate = this.datepipe.transform(row.releaseDate, 'dd-MMM-yyyy'));

      // mat-table init
      this.dataSource = new MatTableDataSource(this.deliverires);

      if (firstRequest) {

        this.dataSource.paginator = this.paginator;
        // this.paginator.pageIndex=page;
        // this.dataSource.paginator.pageIndex=page;
        // sorting date
        this.dataSource.sortingDataAccessor = (item, property): string | number => {
          switch (property) {
            case 'releaseDate': {

              return this.datepipe.transform(item.releaseDate, 'yyyy-MM-dd');
            }
            default: return item[property];
          }
        };
        this.dataSource.sort = this.sort;
      }
      this.dataTableIsLoading = false;
    }).catch(error => {
      this.dataTableIsLoading = false;
      this.deliverires = [];
      this.dataSource = new MatTableDataSource(this.deliverires);
      this.length = 0;
      return;
    });
    this.paginator.pageIndex = page;
    localStorage.removeItem('deliverySearchInput');
  }

  setPage(index: number) {
    this.paginator.pageIndex = index;
  }
  // event when pressing next or previous page in mat-table to request the specified page number from the service.
  paginatorEvent(event) {
    if (this.body) {
      this.getDeliveries(this.body, event.pageIndex, event.pageSize, false);
    }
  }


  // if the value returned from autocomplete component is of type { id, fullName }
  // just return the fullName field ( for delivered by, source, client).
  returnFullNameFieldIfExist(object: any): string {
    if (object) {
      if (object.fullName) {
        return object.fullName;
      } else {
        return object;
      }
    }

  }

  openEditForm(delivery: DeliveryView) {
    this.stateService.data = delivery;
    localStorage.setItem('deliverySearchInput', JSON.stringify(this.body));
    localStorage.setItem('paginator-page-index', JSON.stringify(this.paginator.pageIndex));
    localStorage.setItem('paginator-page-size', this.paginator.pageSize.toString());



    this.router.navigate(['/delivery-edit-screen']);
  }




  highlight(row) {
    this.selectedRowIndex = row.id;
  }


  update(element: DeliveryView, deliveredFlag?: boolean) {

    this.dataTableIsLoading = true;
    const delivery: Delivery = new Delivery(element.id,
      element.functionality, element.sourceId, element.clientId, element.comments,
      element.ticket, deliveredFlag ? 'O' : element.delivered, element.apprv,
      element.incApp, element.deliveredById, element.createdDate, this.loggedUser.id, null, element.releaseId,
      element.deliveredById, element.typeId);



    const deliveryDetails: DeliveryDetails = new DeliveryDetails(null,
      element.appl.split(', '), element.deliveredById, element.createdDate, this.loggedUser.id, null);

    const insertBody: InsertBody = new InsertBody(delivery, deliveryDetails);
    const httpParam: HttpParams = new HttpParams().append('updateFlag', 'true');
    this.deliveryRequestService.insertDelivery(insertBody, httpParam).then(data => {



      this.toastr.success('Successfully ' + 'Updated');
      this.dataTableIsLoading = false;

      if (data.message) {
        this.alertDialog.confirm('Appl Warinings', data.message, 'OK', 'null', 'lg');
      }
      this.getDeliveries(this.body, this.paginator.pageIndex, this.paginator.pageSize, false);
    }).catch(error => {
      this.dataTableIsLoading = false;
    });
  }

  openAppl(element) {


    this.stateService.data = element.appl;

    this.router.navigate(['/appl-screen']);
  }
}
