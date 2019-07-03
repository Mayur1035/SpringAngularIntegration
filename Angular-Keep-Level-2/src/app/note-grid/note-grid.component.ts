import { Component, OnInit, Input } from '@angular/core';
import { Note } from '../note';

@Component({
  selector: 'app-note-grid',
  templateUrl: './note-grid.component.html',
  styleUrls: ['./note-grid.component.css']
})
export class NoteGridComponent implements OnInit {

  @Input()
  notes: Array<Note> = [];

  constructor() { }

  ngOnInit() {
  }

}
