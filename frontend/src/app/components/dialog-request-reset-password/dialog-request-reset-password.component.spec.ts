import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogRequestResetPasswordComponent } from './dialog-request-reset-password.component';

describe('DialogRequestResetPasswordComponent', () => {
  let component: DialogRequestResetPasswordComponent;
  let fixture: ComponentFixture<DialogRequestResetPasswordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogRequestResetPasswordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogRequestResetPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
