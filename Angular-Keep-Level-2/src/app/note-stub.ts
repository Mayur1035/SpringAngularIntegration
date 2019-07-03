import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import { Note } from './note';

export class NoteStub {
    addNote(note: Note): Observable<Note> {
        if (note.title === 'invalid' && note.text === 'invalid') {
            return Observable.throw({ message: 'your values are invalid' });
        }
        return Observable.of(note);
    }
}
