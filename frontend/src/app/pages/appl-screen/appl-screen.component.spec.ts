import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplScreenComponent } from './appl-screen.component';

describe('ApplScreenComponent', () => {
  let component: ApplScreenComponent;
  let fixture: ComponentFixture<ApplScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
