import { Component, OnInit, Input } from '@angular/core';
import { Note } from '../note';
import { RouterService } from '../services/router.service';
import { MatSnackBar } from '@angular/material';
import { FormControl } from '@angular/forms';
import { Category } from '../category';
import { Reminder } from '../reminder';
import { CategoriesService } from '../services/categories.service';
import { ReminderServiceService } from '../services/reminder-service.service';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.css']
})
export class NoteComponent implements OnInit {

  @Input()
  note: Note;
  category: Category;
  reminder: Reminder;

  constructor(private routerSvc: RouterService, public snackBar: MatSnackBar,
    private categorySvc: CategoriesService,
    private reminderSvc: ReminderServiceService) { }

  ngOnInit() {
    this.category = this.categorySvc.getCategoryById(this.note.category.categoryId);
    this.reminder = this.reminderSvc.getReminderById(this.note.reminders.reminderId);
  }

  openEditNoteView() {
    this.routerSvc.routeToEditNoteView(this.note.id);
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
    });
  }

}
