import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModulesEditScreenComponent } from './modules-edit-screen.component';

describe('ModulesEditScreenComponent', () => {
  let component: ModulesEditScreenComponent;
  let fixture: ComponentFixture<ModulesEditScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModulesEditScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModulesEditScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
