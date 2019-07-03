import { Component, OnInit } from '@angular/core';
import { Category } from '../../category';
import { CategoriesService } from '../../services/categories.service';

@Component({
  selector: 'app-category-taker',
  templateUrl: './category-taker.component.html',
  styleUrls: ['./category-taker.component.css']
})
export class CategoryTakerComponent implements OnInit {

  private category : Category;
  errorMsg:string;

  constructor(private categorySvc : CategoriesService) { }

  ngOnInit() {
    this.category = new Category();
  }

  addCategory(){
    console.log(this.category);
    if(this.category.categoryName && this.category.categoryDescription){
      this.categorySvc.addCategory(this.category).subscribe(
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
      this.category = new Category();
      this.errorMsg='';
    }else{
      this.errorMsg= 'Please input required fields';
    }
  }

}
