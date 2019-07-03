import { Component, OnInit, Input } from '@angular/core';
import { Reminder } from '../../reminder';
import { RouterService } from '../../services/router.service';

@Component({
  selector: 'app-reminder',
  templateUrl: './reminder.component.html',
  styleUrls: ['./reminder.component.css']
})
export class ReminderComponent implements OnInit {

  @Input ()
  reminder : Reminder;

  constructor(private routerSvc : RouterService) { }

  ngOnInit() {
  }
  openEditView(){
    this.routerSvc.routeToEditReminderView(this.reminder.reminderId);
  }

}
