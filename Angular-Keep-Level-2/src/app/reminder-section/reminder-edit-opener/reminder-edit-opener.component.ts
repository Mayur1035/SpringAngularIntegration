import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ActivatedRoute } from '@angular/router';
import { RouterService } from '../../services/router.service';
import { ReminderEditorComponent } from '../reminder-editor/reminder-editor.component';

@Component({
  selector: 'app-reminder-edit-opener',
  templateUrl: './reminder-edit-opener.component.html',
  styleUrls: ['./reminder-edit-opener.component.css']
})
export class ReminderEditOpenerComponent implements OnInit {

  constructor(private dialog : MatDialog,
    private activatedRoute : ActivatedRoute,
    private routerSvc : RouterService) { 
      const reminderId = this.activatedRoute.snapshot.paramMap.get('reminderid');
      this.dialog.open(ReminderEditorComponent,
        {
          data:{
            reminderId : reminderId
          }
        }
      ).afterClosed().subscribe(
        result => {
          this.routerSvc.routeBack();
        }
      );
    }

  ngOnInit() {
  }

}
