import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { NotesService } from '../services/notes.service';
import { RouterService } from '../services/router.service';
import { Category } from '../category';
import { Reminder } from '../reminder';
import { CategoriesService } from '../services/categories.service';
import { ReminderServiceService } from '../services/reminder-service.service';
@Component({
	selector: 'app-list-view',
	templateUrl: './list-view.component.html',
	styleUrls: ['./list-view.component.css']
})
export class ListViewComponent implements OnInit {

	note: Note;
	notesArray: Array<Note> = [];
	notStartedNotes: Array<Note>;
	startedNotes: Array<Note>;
	completedNotes: Array<Note>;
	categorizedNotes: Array<Note>;
	remindedNotes: Array<Note>;
	categories: Array<Category> = [];
	reminders: Array<Reminder> = [];

	constructor(private noteService: NotesService,
		private categorySvc: CategoriesService,
		private reminderSvc: ReminderServiceService) {
		this.notStartedNotes = [];
		this.startedNotes = [];
		this.completedNotes = [];
	}

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
		this.noteService.getNotes().subscribe(
			data => {
				this.notesArray = data;
				this.notStartedNotes = this.notesArray.filter((note) => {
					return note.state === 'not-started';
				});
				this.startedNotes = this.notesArray.filter((note) => {
					return note.state === 'started';
				});
				this.completedNotes = this.notesArray.filter((note) => {
					return note.state === 'completed';
				});
			},
			error => console.log(error)
		);
	}

	filterCategory(categoyId) {
		//console.log(categoyId);
		this.categorizedNotes = this.notesArray.filter((note) => {
			return note.category.categoryId === categoyId;
		});
	}

	filterReminder(reminderId) {
		//console.log(reminderId);
		this.remindedNotes = this.notesArray.filter((note) => {
			return note.reminders.reminderId === reminderId;
		});
	}

}