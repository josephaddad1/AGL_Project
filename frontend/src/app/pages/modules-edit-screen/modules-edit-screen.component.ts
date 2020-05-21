import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormCanDeactivate } from 'src/app/service/refresh-can-deactice-guard/can-deactivate.component';
import { StateService } from 'src/app/service/state-service/state.service';

@Component({
  selector: 'app-modules-edit-screen',
  templateUrl: './modules-edit-screen.component.html',
  styleUrls: ['./modules-edit-screen.component.css']
})
export class ModulesEditScreenComponent extends FormCanDeactivate implements OnInit {
  moduleForm: FormGroup;
  isSubmited: boolean;

  module;


  get form(): FormGroup {
    return this.moduleForm;
  }
  get formIsSubmitted(): boolean {
    return this.isSubmited;
  }
  constructor(private state: StateService) { super(); }

  ngOnInit() {
    this.module = this.state.data;
  }

  getForm(event) {
    this.moduleForm = event;
  }

  getIsSubmitted(event) {
    this.isSubmited = event;
  }

}
