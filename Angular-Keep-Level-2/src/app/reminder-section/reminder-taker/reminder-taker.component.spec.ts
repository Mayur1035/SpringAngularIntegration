import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReminderTakerComponent } from './reminder-taker.component';

describe('ReminderTakerComponent', () => {
  let component: ReminderTakerComponent;
  let fixture: ComponentFixture<ReminderTakerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReminderTakerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReminderTakerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
