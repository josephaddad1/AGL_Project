import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogApplContentComponent } from './dialog-appl-content.component';

describe('DialogApplContentComponent', () => {
  let component: DialogApplContentComponent;
  let fixture: ComponentFixture<DialogApplContentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogApplContentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogApplContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
