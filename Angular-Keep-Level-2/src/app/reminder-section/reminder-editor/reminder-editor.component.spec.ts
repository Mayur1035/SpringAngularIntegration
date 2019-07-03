import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReminderEditorComponent } from './reminder-editor.component';

describe('ReminderEditorComponent', () => {
  let component: ReminderEditorComponent;
  let fixture: ComponentFixture<ReminderEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReminderEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReminderEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
