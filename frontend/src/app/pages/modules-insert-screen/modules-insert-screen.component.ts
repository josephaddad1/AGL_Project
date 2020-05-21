import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormCanDeactivate } from 'src/app/service/refresh-can-deactice-guard/can-deactivate.component';

@Component({
  selector: 'app-modules-insert-screen',
  templateUrl: './modules-insert-screen.component.html',
  styleUrls: ['./modules-insert-screen.component.css']
})
export class ModulesInsertScreenComponent extends FormCanDeactivate implements OnInit {
  moduleForm: FormGroup;
  isSubmited: boolean;


  get form(): FormGroup {
    return this.moduleForm;
  }
  get formIsSubmitted(): boolean {
    return this.isSubmited;
  }
  constructor() { super(); }

  ngOnInit() {
  }

  getForm(event) {
    this.moduleForm = event;
  }

  getIsSubmitted(event) {
    this.isSubmited = event;
  }

}
