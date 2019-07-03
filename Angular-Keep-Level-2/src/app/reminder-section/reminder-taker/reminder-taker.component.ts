import { Component, OnInit } from '@angular/core';
import { Reminder } from '../../reminder';
import { ReminderServiceService } from '../../services/reminder-service.service';

@Component({
  selector: 'app-reminder-taker',
  templateUrl: './reminder-taker.component.html',
  styleUrls: ['./reminder-taker.component.css']
})
export class ReminderTakerComponent implements OnInit {

  private reminder : Reminder;
  errorMsg:string;

  constructor(private reminderSvc : ReminderServiceService) { }

  ngOnInit() {
    this.reminder = new Reminder();
  }

  addReminder(){
    console.log(this.reminder);
    if(this.reminder.reminderName && this.reminder.reminderType){
      this.reminderSvc.addReminders(this.reminder).subscribe(
       data=> {
        (<any>document.querySelector("#note-taker-form")).reset();
       },
        err => {
          if(err.error.message){
            this.errorMsg= err.error.message;
          }else{
            this.errorMsg = err.message;
          }
          
        }
      );
      this.reminder = new Reminder();
      this.errorMsg='';
    }else{
      this.errorMsg= 'Please input required fields';
    }
  }

}
