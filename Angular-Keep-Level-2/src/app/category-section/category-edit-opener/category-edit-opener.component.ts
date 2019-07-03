import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ActivatedRoute } from '@angular/router';
import { RouterService } from '../../services/router.service';
import { CatergoryEditorComponent } from '../catergory-editor/catergory-editor.component';

@Component({
  selector: 'app-category-edit-opener',
  templateUrl: './category-edit-opener.component.html',
  styleUrls: ['./category-edit-opener.component.css']
})
export class CategoryEditOpenerComponent implements OnInit {

  constructor(private dialog : MatDialog,
    private activatedRoute : ActivatedRoute,
    private routerSvc : RouterService) { 
      const categoryId = this.activatedRoute.snapshot.paramMap.get('categoryid');
      this.dialog.open(CatergoryEditorComponent,
        {
          data:{
            categoryId : categoryId
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
