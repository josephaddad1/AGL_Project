import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModulesSearchScreenComponent } from './modules-search-screen.component';

describe('ModulesSearchScreenComponent', () => {
  let component: ModulesSearchScreenComponent;
  let fixture: ComponentFixture<ModulesSearchScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModulesSearchScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModulesSearchScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
