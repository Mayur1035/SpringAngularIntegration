import { TestBed, inject } from '@angular/core/testing';

import { ReminderServiceService } from './reminder-service.service';

describe('ReminderServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReminderServiceService]
    });
  });

  it('should be created', inject([ReminderServiceService], (service: ReminderServiceService) => {
    expect(service).toBeTruthy();
  }));
});
