import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { NotesService } from '../services/notes.service';
import { Category } from '../category';
import { CategoriesService } from '../services/categories.service';
import { Reminder } from '../reminder';
import { ReminderServiceService } from '../services/reminder-service.service';

@Component({
  selector: 'app-note-taker',
  templateUrl: './note-taker.component.html',
  styleUrls: ['./note-taker.component.css']
})
export class NoteTakerComponent implements OnInit {

  note : Note;
  errorMsg:string;
  step = 9;
  categories: Array<Category> = [];
  reminders: Array<Reminder> = [];
  category: Category;
  reminder : Reminder;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

  constructor( private noteSvc : NotesService, 
    private categorySvc : CategoriesService, private reminderSvc : ReminderServiceService) { }

  ngOnInit() {
    this.note = new Note();
    this.categorySvc.getCategories().subscribe(
      data => this.categories = data,
      err => console.log(err)
    );
    this.reminderSvc.getReminders().subscribe(
      data => this.reminders = data,
      err => console.log(err)
    );
  }

  takeNotes() {
    if(this.note.title && this.note.text){
      this.category = this.categorySvc.getCategoryById(this.note.category.categoryId);
      this.reminder = this.reminderSvc.getReminderById(this.note.reminders.reminderId);
      this.note.category.categoryName = this.category.categoryName;
      this.note.category.categoryDescription= this.category.categoryDescription;
      this.note.reminders.reminderName=this.reminder.reminderName;
      this.note.reminders.reminderType=this.reminder.reminderType;
      console.log(this.note);
      this.noteSvc.addNote(this.note).subscribe(
       data=> {
        (<any>document.querySelector("#note-taker-form")).reset();
        this.setStep(9);
       },
        err => {
          if(err.error.message){
            this.errorMsg= err.error.message;
          }else{
            this.errorMsg = err.message;
          }
          
        }
      );
      this.note = new Note();
      this.errorMsg='';
    }else{
      this.errorMsg= 'Title and Text both are required fields';
    }
  }
}
