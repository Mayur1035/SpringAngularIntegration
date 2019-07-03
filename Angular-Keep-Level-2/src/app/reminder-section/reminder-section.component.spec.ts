import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReminderSectionComponent } from './reminder-section.component';

describe('ReminderSectionComponent', () => {
  let component: ReminderSectionComponent;
  let fixture: ComponentFixture<ReminderSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReminderSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReminderSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
