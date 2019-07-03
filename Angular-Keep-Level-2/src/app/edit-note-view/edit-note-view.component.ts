import { Component, OnInit, Inject } from '@angular/core';
import {MatDialog , MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { Note } from '../note';
import { NotesService } from '../services/notes.service';
import { Category } from '../category';
import { CategoriesService } from '../services/categories.service';
import { Reminder } from '../reminder';
import { ReminderServiceService } from '../services/reminder-service.service';

@Component({
  selector: 'app-edit-note-view',
  templateUrl: './edit-note-view.component.html',
  styleUrls: ['./edit-note-view.component.css']
})
export class EditNoteViewComponent implements OnInit {

  note: Note;
  category: Category;
  reminder: Reminder;
  categories: Array<Category> = [];
  reminders: Array<Reminder> = [];
  
  errorMessage : string;
  constructor(private dialogRef : MatDialogRef<EditNoteViewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private noteSvc : NotesService,
    private categorySvc: CategoriesService,
    private reminderSvc: ReminderServiceService
  ) { 
  } 

  ngOnInit() {
    this.note = this.noteSvc.getNoteById(this.data.noteId);
    this.category = this.categorySvc.getCategoryById(this.note.category.categoryId);
    this.reminder = this.reminderSvc.getReminderById(this.note.reminders.reminderId);
    this.categorySvc.getCategories().subscribe(
      data => this.categories = data,
      err => console.log(err)
    );
    this.reminderSvc.getReminders().subscribe(
      data => this.reminders = data,
      err => console.log(err)
    );
  }

  editNotes(){
    this.noteSvc.editNote(this.note).subscribe(
      editedNote => {
        this.dialogRef.close();
      },
      err => {
        if(err.error.message){
          this.errorMessage= err.error.message;
        }else{
          this.errorMessage = err.message;
        }
      }
    );
  }

  deleteNote(){
    this.noteSvc.deleteNote(this.note).subscribe(
      editedNote => {
        this.dialogRef.close();
      },
      err => {
        if(err.error.message){
          this.errorMessage= err.error.message;
        }else{
          this.errorMessage = err.message;
        }
      }
    );
  }

}
