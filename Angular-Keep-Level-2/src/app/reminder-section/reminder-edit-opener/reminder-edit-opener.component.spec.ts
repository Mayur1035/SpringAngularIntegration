import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReminderEditOpenerComponent } from './reminder-edit-opener.component';

describe('ReminderEditOpenerComponent', () => {
  let component: ReminderEditOpenerComponent;
  let fixture: ComponentFixture<ReminderEditOpenerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReminderEditOpenerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReminderEditOpenerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
