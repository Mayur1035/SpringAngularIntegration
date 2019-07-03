import { Injectable } from '@angular/core';
import { Category } from '../category';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { AuthenticationService } from './authentication.service';
import { Observable } from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class CategoriesService {

  token: any;
  categories: Array<Category>;
  categorySubject: BehaviorSubject<Array<Category>>;
  private categorySvcUrl: string;

  constructor(private http: HttpClient,
    private authSvc: AuthenticationService) {
    this.categorySvcUrl = 'http://localhost:8083/api/v1/category/';
    this.categories = [];
    this.categorySubject = new BehaviorSubject(this.categories);
    this.fetchCategoriesFromServer();
  }

  fetchCategoriesFromServer() {
    this.token = this.authSvc.getBearerToken();
    return this.http.get<Array<Category>>(this.categorySvcUrl, {
      headers: new HttpHeaders()
        .set('Authorization', `Bearer ${this.token}`)
    }).subscribe(
      categoriesArray => {
        this.categories = categoriesArray;
        this.categorySubject.next(this.categories);
      },
      err => {
        console.log(err);
      }
      );
  }

  getCategories(): Observable<Array<Category>> {
    return this.categorySubject;
  }
  

  addCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(this.categorySvcUrl, category,
      {
        headers: new HttpHeaders()
          .set('Authorization', `Bearer ${this.token}`)
      }).do(
      addedCategory => {
        this.categories.push(addedCategory);
        this.categorySubject.next(this.categories);
      },
      err => {
        console.log(err);
      }
      );
  }

  getCategoryById(categoryId): Category {
    const category = this.categories.find(category => category.categoryId === categoryId);
    return Object.assign({}, category);
  }

  editCategory(category): Observable<Category> {
    return this.http.put<Category>(`${this.categorySvcUrl}${category.categoryId}`, category
      , {
        headers: new HttpHeaders()
          .set('Authorization', `Bearer ${this.token}`)
      }).do(
      editedCategory => {
        const category = this.categories.find(category => category.categoryId === editedCategory.categoryId);
        Object.assign(category, editedCategory);
        this.categorySubject.next(this.categories);
      },
      err => {
        console.log(err);
      }
      );
  }

  deleteCategory(category: Category): Observable<Category> {
    return this.http.delete<Category>(`${this.categorySvcUrl}${category.categoryId}`,
      {
        headers: new HttpHeaders()
          .set('Authorization', `Bearer ${this.token}`)
      }).do(
      deletedNote => {
        this.categories.pop();
        this.categorySubject.next(this.categories);
      },
      err => {
        console.log(err);
      }
      );
  }

}
