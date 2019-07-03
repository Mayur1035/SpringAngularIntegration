import { Component, OnInit, Inject } from '@angular/core';
import { Category } from '../../category';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { CategoriesService } from '../../services/categories.service';

@Component({
  selector: 'app-catergory-editor',
  templateUrl: './catergory-editor.component.html',
  styleUrls: ['./catergory-editor.component.css']
})
export class CatergoryEditorComponent implements OnInit {

  category: Category;
  errorMessage: string;

  constructor(private dialogRef: MatDialogRef<CatergoryEditorComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private categorySvc: CategoriesService) { }

  ngOnInit() {
    this.category = this.categorySvc.getCategoryById(this.data.categoryId);
  }

  editCategory() {
    this.categorySvc.editCategory(this.category).subscribe(
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

  deleteCategory() {
    this.categorySvc.deleteCategory(this.category).subscribe(
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
