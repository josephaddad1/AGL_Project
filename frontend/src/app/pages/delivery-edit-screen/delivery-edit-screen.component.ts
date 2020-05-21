import { Component, OnInit, Input } from '@angular/core';
import { DeliveryFormComponent } from 'src/app/components/delivery-form/delivery-form.component';
import { MatDialogRef } from '@angular/material';
import { FormCanDeactivate } from 'src/app/service/refresh-can-deactice-guard/can-deactivate.component';
import { FormGroup } from '@angular/forms';
import { DialogService } from 'src/app/components/dialog-alert/dialog.service';
import { Delivery } from 'src/app/interface/delivery.interface';
import { StateService } from 'src/app/service/state-service/state.service';
import { DeliveryView } from 'src/app/interface/DeliveryView.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-delivery-edit-screen',
  templateUrl: './delivery-edit-screen.component.html',
  styleUrls: ['./delivery-edit-screen.component.css']
})
export class DeliveryEditScreenComponent extends FormCanDeactivate implements OnInit {
  isSubmitted = false;
  deliveryForm: FormGroup;

  constructor(
    private alertDialog: DialogService, private state: StateService, private router: Router) { super(); }


  data: DeliveryView;

  get form(): FormGroup {
    return this.deliveryForm;
  }
  get formIsSubmitted(): boolean {
    return this.isSubmitted;
  }


  getIsSubmitted(bool: boolean) {
    this.isSubmitted = bool;
  }

  getForm(form) {
    this.deliveryForm = form;
    return this.deliveryForm;
  }
  ngOnInit() {
    this.data = this.state.data;
  }

  async cancel() {

    if (!this.isSubmitted && this.deliveryForm.dirty) {

      let bool;
      await this.alertDialog.confirm('Attention!!', 'Changes detected do eally want to discard?').then(data => {
        if (data) {
          bool = data;
        }
      });
      if (bool) {
        this.router.navigate(['/delivery-search-screen']);
      }

    } else {
      this.router.navigate(['/delivery-search-screen']);

    }

  }

}
