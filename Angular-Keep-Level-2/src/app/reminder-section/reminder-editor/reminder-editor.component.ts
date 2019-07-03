import { Component, OnInit, Inject } from '@angular/core';
import { Reminder } from '../../reminder';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ReminderServiceService } from '../../services/reminder-service.service';

@Component({
  selector: 'app-reminder-editor',
  templateUrl: './reminder-editor.component.html',
  styleUrls: ['./reminder-editor.component.css']
})
export class ReminderEditorComponent implements OnInit {

  reminder: Reminder;
  errorMessage: string;

  constructor(private dialogRef: MatDialogRef<ReminderEditorComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private reminderSvc: ReminderServiceService) { }

  ngOnInit() {
    this.reminder = this.reminderSvc.getReminderById(this.data.reminderId);
  }

  editReminder() {
    this.reminderSvc.editReminder(this.reminder).subscribe(
      editedNote => {
        this.dialogRef.close();
      },
      err => {
        if (err.error.message) {
          this.errorMessage = err.error.message;
        } else {
          this.errorMessage = err.message;
        }
      }
    );
  }

  deleteReminder() {
    this.reminderSvc.deleteReminder(this.reminder).subscribe(
      editedNote => {
        this.dialogRef.close();
      },
      err => {
        if (err.error.message) {
          this.errorMessage = err.error.message;
        } else {
          this.errorMessage = err.message;
        }
      }
    );
  }

}
