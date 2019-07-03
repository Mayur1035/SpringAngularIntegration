import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditNoteViewComponent } from './edit-note-view.component';

describe('EditNoteViewComponent', () => {
  let component: EditNoteViewComponent;
  let fixture: ComponentFixture<EditNoteViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditNoteViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditNoteViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
