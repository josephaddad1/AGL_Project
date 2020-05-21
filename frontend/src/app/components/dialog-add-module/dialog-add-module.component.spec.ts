import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAddModuleComponent } from './dialog-add-module.component';

describe('DialogAddModuleComponent', () => {
  let component: DialogAddModuleComponent;
  let fixture: ComponentFixture<DialogAddModuleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogAddModuleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAddModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
