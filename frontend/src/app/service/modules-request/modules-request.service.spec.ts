import { TestBed } from '@angular/core/testing';

import { ModulesRequestService } from './modules-request.service';

describe('ModulesRequestService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ModulesRequestService = TestBed.get(ModulesRequestService);
    expect(service).toBeTruthy();
  });
});
