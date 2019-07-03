import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { NoteTakerComponent } from './note-taker.component';
import { Note } from '../note';
import { MatExpansionModule} from '@angular/material/expansion';
import { MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule} from '@angular/material/input';
import { MatButtonModule} from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { NotesService } from '../services/notes.service';
import { NoteStub } from '../note-stub';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('NoteTakerComponent', () => {
  let component: NoteTakerComponent;
  let fixture: ComponentFixture<NoteTakerComponent>;
  let service: NotesService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NoteTakerComponent ],
      imports: [
        MatExpansionModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatSelectModule,
        FormsModule,
        BrowserAnimationsModule
      ],
      providers: [
        {provide: NotesService, useClass: NoteStub }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteTakerComponent);
    component = fixture.componentInstance;
    service = fixture.debugElement.injector.get(NotesService);
    // fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have input element for note title', () => {
    const de = fixture.debugElement.query(By.css('input'));
    expect(de).toBeTruthy();
    expect(de.nativeElement.value).toBe('');
  });

  it('should have text area element for note text', () => {
    const de = fixture.debugElement.query(By.css('textarea'));
    expect(de).toBeTruthy();
    expect(de.nativeElement.value).toBe('');
  });

  it('should have button element for submit note with value Done', () => {
    const de = fixture.debugElement.query(By.css('button'));
    expect(de).toBeTruthy();
    expect(de.nativeElement.textContent).toBe('Done');
  });

  it('should note be able to take note if input for note title is blank, button is diabled', () => {
    const testNote = new Note();
    testNote.text = 'text1';
    component.note = testNote;

    const de = fixture.debugElement.query(By.css('button'));
    expect(de.nativeElement.disabled).toBe(false);
  });

  it('should have input element for note title', () => {
    spyOn(service, 'addNote').and.callThrough();

    const testNote = new Note();
    testNote.text = 'text1';
    component.note = testNote;
    fixture.detectChanges();

    const de = fixture.debugElement.query(By.css('button'));
    de.nativeElement.click();
    fixture.detectChanges();
    expect(service.addNote).toHaveBeenCalledWith(testNote);
  });

});
