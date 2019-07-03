import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import {Note} from '../note';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import 'rxjs/add/operator/do';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class NotesService {
  token : any;
  notes : Array<Note>;
  notesSubject : BehaviorSubject<Array<Note>>;
  private noteSvcUrl: string;

  constructor(private http: HttpClient,
              private authSvc : AuthenticationService) { 
    this.noteSvcUrl='http://localhost:8082/api/v1/note/';
    //this.noteSvcUrl='http://localhost:3000/notes';
    this.notes = [];
    this.notesSubject = new BehaviorSubject(this.notes);
    this.fetchNotesFromServer();
  }

  fetchNotesFromServer(){
    this.token = this.authSvc.getBearerToken();
    return this.http.get<Array<Note>>(this.noteSvcUrl,{
          headers : new HttpHeaders()
          .set('Authorization', `Bearer ${this.token}`)
    }).subscribe(
      notesArray => {
        this.notes = notesArray;
        this.notesSubject.next(this.notes);
      },
      err =>{
        console.log(err);
      }
    );
  }

  getNotes(): Observable<Array<Note>>{
    return this.notesSubject;
  }

  addNote(note: Note): Observable<Note>{
    return this.http.post<Note>(this.noteSvcUrl, note,
    {
      headers : new HttpHeaders()
      .set('Authorization', `Bearer ${this.token}`)
    }).do(
      addedNote => {
        this.notes.push(addedNote);
        this.notesSubject.next(this.notes);
      },
      err =>{
        console.log(err);
      }
    );
  }

  getNoteById(noteId):Note{
    const note = this.notes.find(note => note.id === noteId);
    return Object.assign({}, note);
  }

  editNote(note) : Observable<Note>{
    return this.http.put<Note>(`${this.noteSvcUrl}${note.id}`, note
    ,{
      headers : new HttpHeaders()
      .set('Authorization', `Bearer ${this.token}`)
    }).do(
      editedNote => {
        const note = this.notes.find(note => note.id === editedNote.id);
        Object.assign(note , editedNote);
        this.notesSubject.next(this.notes);
      },
      err =>{
        console.log(err);
      }
    );
  }

  deleteNote(note: Note): Observable<Note>{
    return this.http.delete<Note>(`${this.noteSvcUrl}${note.id}`,
    {
      headers : new HttpHeaders()
      .set('Authorization', `Bearer ${this.token}`)
    }).do(
      deletedNote => {
        this.notes.pop();
        this.notesSubject.next(this.notes);
      },
      err =>{
        console.log(err);
      }
    );
  }


}
