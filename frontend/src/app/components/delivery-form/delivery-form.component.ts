import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormControl, Validators, Form } from '@angular/forms';
import { DeliveryView } from 'src/app/interface/DeliveryView.interface';
import { distinctUntilChanged, debounceTime, subscribeOn } from 'rxjs/operators';
import { DeliveryRequestService } from 'src/app/service/delivery-request/delivery-request.service';
import { Delivery } from 'src/app/interface/delivery.interface';
import { InsertBody } from 'src/app/interface/insert-body.interface';
import { DeliveryDetails } from 'src/app/interface/delivery-details.interface';
import { ToastrService } from 'ngx-toastr';
import { User } from 'src/app/interface/user.interface';
import { CustomIdFullName } from 'src/app/interface/custom-id-full-name.interface';
import { RolesService } from 'src/app/service/roles-service/roles.service';
import { DialogService } from '../dialog-alert/dialog.service';
import { MatDialogRef, MatAutocompleteTrigger } from '@angular/material';
import { Router } from '@angular/router';
import { Constants } from 'src/environments/constants';
import { HttpParams } from '@angular/common/http';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-delivery-form',
  templateUrl: './delivery-form.component.html',
  styleUrls: ['./delivery-form.component.css'],
  providers: [
    { provide: MatDialogRef, useValue: {} },]
})
export class DeliveryFormComponent implements OnInit {
  deliveryForm: FormGroup;

  applForm: FormGroup;
  applList: string[] = [];
  subscription: Subscription;
  trigger;
  deliveredByArray: CustomIdFullName[];
  clientsArray: CustomIdFullName[];
  sourceArray: CustomIdFullName[];
  typesArray: CustomIdFullName[];



  isLoading = false;
  loadder = false;

  body: DeliveryView;

  // delivert types
  newId;

  userRole;
  loggedUser: CustomIdFullName;

  apprvFlag: any = false;
  incAppFlag: any = false;
  deliveredFlag: any = false;

  // when call this component to edit a delivery
  @Input() data: DeliveryView;
  @Input() formType: string;

  @Output() formToParent = new EventEmitter<FormGroup>();
  @Output() isSubmittedToParent = new EventEmitter<boolean>();
  oldValue: any;



  constructor(private deliveryRequestService: DeliveryRequestService, private toastr: ToastrService, private roleService: RolesService
    , private alertDialog: DialogService, public dialogRef: MatDialogRef<DeliveryFormComponent>, private router: Router) {
  }


  async  ngOnInit() {
    this.loadder = true;
    Constants.userData = JSON.parse(localStorage.getItem('userData'));


    await this.deliveryRequestService.getTypes().then(types => {
      if (types) {
        this.typesArray = types.payLoad;
        this.newId = this.typesArray[0].id;
      }
    });

    await this.roleService.retrieveRoleCode(Constants.userData).then(role => {
      this.userRole = role;
    });

    this.incAppFlag = this.data ? this.data.incApp === 'O' ? true : false : false;
    this.apprvFlag = this.data ? this.data.apprv === 'O' ? true : false : false;
    this.deliveredFlag = this.data ? this.data.delivered === 'O' ? true : false : false;


    this.loggedUser = new CustomIdFullName(Constants.userData.id, Constants.userData.fullName);

    // if this.data is not null then this is an edit mode

    this.initForm(this.data ? this.data : null);

    this.formToParent.emit(this.deliveryForm);



    this.autoCompleteOnValueChange('clientInput', 'client');
    this.autoCompleteOnValueChange('sourceInput', 'source');
    this.autoCompleteOnValueChange('deliveredByInput', 'deliveredBy');
    this.loadder = false;


  }


  initForm(delivery?: DeliveryView) {

    this.deliveryForm = new FormGroup({
      functionalityInput: new FormControl(delivery ? delivery.functionality : null, Validators.required),

      deliveredByInput: new FormControl({
        value: delivery ? new CustomIdFullName(delivery.deliveredById, delivery.deliveredBy) : this.loggedUser,
        disabled: this.userRole === 'level-1' ? false : true
      }, [Validators.required, this.objectIsValid]),

      sourceInput: new FormControl(
        delivery ? new CustomIdFullName(delivery.sourceId, delivery.source) : null
        , [Validators.required, this.objectIsValid]),

      ticketInput: new FormControl(delivery ? delivery.ticket : null, [Validators.required, Validators.pattern(/[A-Z]+\-[0-9]+/)]),

      clientInput: new FormControl(
        delivery ? new CustomIdFullName(delivery.clientId, delivery.client) : null
        , [Validators.required, this.objectIsValid]),

      typeInput: new FormControl(delivery ? delivery.typeId : this.newId, Validators.required),

      applList: new FormControl(null),

      commentInput: new FormControl(delivery ? delivery.comments : null),
    });

    this.applList = delivery ? delivery.appl.split(', ') : [];
    this.applForm = new FormGroup({

      applList: new FormControl(null, [Validators.required])
    });
  }


  // on value change of form input send request to server to get the lov(client, source, delivered by)
  autoCompleteOnValueChange(formControlName, input) {

    this.subscription = this.deliveryForm.get(formControlName).valueChanges.pipe(
      debounceTime(300),
      // make sure if the value is differente from the old one
      distinctUntilChanged(),

    ).subscribe(changedValue => {

      if(changedValue && (changedValue.fullName || changedValue.id)){
        return
      }
     this.oldValue= changedValue;
      this.isLoading = true;
      let val = this.returnFullNameFieldIfExist(changedValue);



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
        this.deliveryForm.patchValue({ [formControlName]: x });
        this.trigger = Math.random();
        console.log(formControlName)
        this.autoCompleteOnValueChange(formControlName, input);
        
      }

    })
  }
  // return name fieled from auto complete returned object
  returnFullNameFieldIfExist(object: any): string {
    if (object) {
      if (object.fullName) {
        return object.fullName;
      } else {
        return object;
      }
    }
  }

  // if the value returned from autocomplete component is of type { id, fullName }
  // return the id field ( for delivered by, source, client).
  returnIdFieldIfExist(object: CustomIdFullName): string {
    if (object) {
      if (object.id) {
        return object.id;
      } else {
        return object.fullName;
      }
    }
  }
  onSubmit(deliveryFormEvent: FormGroup) {
    this.isSubmittedToParent.emit(false);
    this.loadder = true;
    let functionality = deliveryFormEvent.get('functionalityInput').value;
    if (!functionality) {
      functionality = null;
    }


    // if the value returned from autocomplete component is of type { id, fullName }
    // just send the name to the search request ( for delivered by, source, client).
    const deliveredById = this.returnIdFieldIfExist(deliveryFormEvent.get('deliveredByInput').value);
    const sourceId = this.returnIdFieldIfExist(deliveryFormEvent.get('sourceInput').value);
    const clientId = this.returnIdFieldIfExist(deliveryFormEvent.get('clientInput').value);

    let apprvTmp;
    let incTmp;
    let deliveredTmp;

    if (this.apprvFlag === true) {
      apprvTmp = 'O';
    } else {
      apprvTmp = 'N';
    }
    if (this.incAppFlag === true) {
      incTmp = 'O';
    } else {
      incTmp = 'N';
    }
    if (this.deliveredFlag === true) {
      deliveredTmp = 'O';
    } else {
      deliveredTmp = 'N';
    }



    // if this data not null then this component is called from edit mode 
    const updatedByUser = this.data ? this.loggedUser : null;
    const releaseId = this.data ? this.data.releaseId : null;
    // date is sent null it will be set in the backend
    const delivery: Delivery = new Delivery(this.data ? this.data.id : null,
      functionality, sourceId, clientId, deliveryFormEvent.get('commentInput').value,
      deliveryFormEvent.get('ticketInput').value, deliveredTmp, apprvTmp,
      incTmp, deliveredById, this.data ? this.data.createdDate : null, updatedByUser ? updatedByUser.id : null, null, releaseId ? releaseId : null,
      deliveredById, deliveryFormEvent.get('typeInput').value);



    const deliveryDetails: DeliveryDetails = new DeliveryDetails(null,
      this.applList, deliveredById, this.data ? this.data.createdDate : null, updatedByUser ? updatedByUser.id : null, null);

    const insertBody: InsertBody = new InsertBody(delivery, deliveryDetails);
    const httpParam: HttpParams = new HttpParams().append('updateFlag', 'false');
    console.log(httpParam)
    this.deliveryRequestService.insertDelivery(insertBody, httpParam).then(data => {

      if ((data.error)) {
        this.alertDialog.confirm('Process Blocked! \n Appl validation Error', data.error, 'OK', 'null', 'lg');
        this.loadder = false;

        return;
      }

      this.toastr.success('Successfully ' + this.formType + 'ed');
      this.deliveryForm.reset();
      this.initForm();
      this.isSubmittedToParent.emit(true);
      this.applList = [];
      this.loadder = false;

      this.router.navigate(['/delivery-search-screen']);

      if (data.message) {
        this.alertDialog.confirm('Appl Warnings', data.message, 'OK', 'null', 'lg');
      }
    }).catch(error => {
      this.loadder = false;
    });

  }

  addAppl(applForm: FormGroup) {
    let applName: string = applForm.get('applList').value;
    let exist: boolean;
    let _null: boolean;
    this.applList.filter(applNameExist => {
      applNameExist ? _null = false : _null = true;
      if (applNameExist === applName) { exist = true; return null; };
    })
    if (!_null && !exist)
      this.applList.push(applName)

    applForm.reset();
  }

  deleteAppl(appl: string) {
    this.applList = this.applList.filter(item => {
      return item !== appl;
    });
  }


  // to disable from button if the applList is empty
  isApplListIsEmpty(): boolean {
    if (this.applList.length === 0) {
      return true;
    }
    return false;
  }


  // disable button if not valid
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
  // delete appl from list
  // toast() {
  //   // this.toastr.success('Succsesfuly added', 'Toastr Fun!');
  //   console.log(prompt('You data is loading. Are you sure you want to leave?'));
  // }

  hasUnsavedData() {
    return this.deliveryForm.dirty;
  }

  delete() {
    this.alertDialog.confirm('Attention!!', 'Do you really want to delete this delivery?').then(data => {

      if (data) {

        this.deliveryRequestService.deleteDelivery(this.data.id).then(data => {
          this.loadder = true;

          this.toastr.success(data.payLoad);
          this.router.navigate(['/delivery-search-screen']);
          this.loadder = false;

        }).catch(error => {
          this.toastr.error(error);

          this.loadder = false;

        });
      }

    });
  }
}
