import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material';
import { EditNoteViewComponent } from '../edit-note-view/edit-note-view.component';
import { ActivatedRoute } from '@angular/router';
import { RouterService } from '../services/router.service';


@Component({
  selector: 'app-edit-note-opener',
  templateUrl: './edit-note-opener.component.html',
  styleUrls: ['./edit-note-opener.component.css']
})
export class EditNoteOpenerComponent implements OnInit {

  constructor( private dialog : MatDialog,
              private activatedRoute : ActivatedRoute,
              private routerSvc : RouterService
    ) { 
      const noteId = this.activatedRoute.snapshot.paramMap.get('noteid');
      this.dialog.open(EditNoteViewComponent,
        {
          data:{
            noteId : noteId
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
