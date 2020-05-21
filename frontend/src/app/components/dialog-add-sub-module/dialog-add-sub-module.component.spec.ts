import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAddSubModuleComponent } from './dialog-add-sub-module.component';

describe('DialogAddSubModuleComponent', () => {
  let component: DialogAddSubModuleComponent;
  let fixture: ComponentFixture<DialogAddSubModuleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogAddSubModuleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAddSubModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
