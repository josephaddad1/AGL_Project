import { Component, OnInit, ViewChild } from '@angular/core';
import { FormCanDeactivate } from 'src/app/service/refresh-can-deactice-guard/can-deactivate.component';
import { DeliveryFormComponent } from 'src/app/components/delivery-form/delivery-form.component';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-delivery-insert-screen',
  templateUrl: './delivery-insert-screen.component.html',
  styleUrls: ['./delivery-insert-screen.component.css']
})
export class DeliveryInsertScreenComponent extends FormCanDeactivate implements OnInit {

  deliveryForm: FormGroup;
  isSubmitted = false;

  get form(): FormGroup {
    return this.deliveryForm;
  }
  get formIsSubmitted(): boolean {
    return this.isSubmitted;
  }

  constructor() {
    super();
  }

  getForm(form: FormGroup) {
    this.deliveryForm = form;
    return this.deliveryForm;
  }
  getIsSubmitted(bool: boolean) {
    this.isSubmitted = bool;
  }
  ngOnInit() {
  }
}
