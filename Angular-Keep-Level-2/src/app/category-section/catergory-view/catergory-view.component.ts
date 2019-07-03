import { Component, OnInit } from '@angular/core';
import { Category } from '../../category';
import { CategoriesService } from '../../services/categories.service';

@Component({
  selector: 'app-catergory-view',
  templateUrl: './catergory-view.component.html',
  styleUrls: ['./catergory-view.component.css']
})
export class CatergoryViewComponent implements OnInit {

  categories: Array<Category> = [];

  constructor(private categorySvc : CategoriesService) { }

  ngOnInit() {
    this.categorySvc.getCategories().subscribe(
      data => this.categories = data,
      err => console.log(err)
    );
  }

}
