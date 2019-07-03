import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { MatCardModule } from '@angular/material/card';
import { NotesService } from '../services/notes.service';
import { NoteStub } from '../note-stub';

import { ListViewComponent } from './list-view.component';
import { NoteComponent } from '../note/note.component';

describe('ListViewComponent', () => {
  let component: ListViewComponent;
  let fixture: ComponentFixture<ListViewComponent>;
  let service: NotesService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListViewComponent, NoteComponent ],
      imports: [ MatCardModule ],
      providers: [
        {provide: NotesService, useClass: NoteStub }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have 3 sections', () => {
    const des = fixture.debugElement.queryAll(By.css('mat-tab'));
    expect(des).toBeTruthy();
    expect(des.length).toBe(3);
  });

});
