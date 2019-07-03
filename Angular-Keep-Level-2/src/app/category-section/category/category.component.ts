import { Component, OnInit, Input } from '@angular/core';
import { Category } from '../../category';
import { RouterService } from '../../services/router.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  @Input ()
  category : Category;

  constructor(private routerSvc : RouterService) { }

  ngOnInit() {
  }

  openEditView(){
    this.routerSvc.routeToEditCategoryView(this.category.categoryId);
  }

}
