import { Component, OnInit } from '@angular/core';
import { Reminder } from '../../reminder';
import { ReminderServiceService } from '../../services/reminder-service.service';

@Component({
  selector: 'app-reminder-view',
  templateUrl: './reminder-view.component.html',
  styleUrls: ['./reminder-view.component.css']
})
export class ReminderViewComponent implements OnInit {

  reminders: Array<Reminder> = [];

  constructor(private reminderSvc : ReminderServiceService) { }

  ngOnInit() {
    this.reminderSvc.getReminders().subscribe(
      data => this.reminders = data,
      err => console.log(err)
    );
  }

}
