import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModulesInsertScreenComponent } from './modules-insert-screen.component';

describe('ModulesInsertScreenComponent', () => {
  let component: ModulesInsertScreenComponent;
  let fixture: ComponentFixture<ModulesInsertScreenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModulesInsertScreenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModulesInsertScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
