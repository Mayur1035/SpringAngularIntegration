import { Injectable } from '@angular/core';
import {Router} from '@angular/router';
import { Location } from '@angular/common';

@Injectable()
export class RouterService {

  constructor(private route:Router, private location:Location) { }

  routeToDashboard(){
    this.route.navigate(['dashboard']);
  }
  routeToCategoryDashboard(){
    this.route.navigate(['category']);
  }
  routeToReminderDashboard(){
    this.route.navigate(['reminder']);
  }

  routeToLogin(){
    this.route.navigate(['login']);
  }

  routeToNewUserForm(){
    this.route.navigate(['new-user']);
  }

  routeToEditNoteView(noteId){
    this.route.navigate(['dashboard' , {
      outlets:{
        noteEditOutlet: ['note', noteId ,'edit']
      }
    }]);
  }
  routeToEditCategoryView(categoryId){
    this.route.navigate(['category' , {
      outlets:{
        categoryEditOutlet: ['category', categoryId ,'edit']
      }
    }]);
  }
  routeToEditReminderView(reminderId){
    this.route.navigate(['reminder' , {
      outlets:{
        reminderEditOutlet: ['reminder', reminderId ,'edit']
      }
    }]);
  }

  routeToListView(){
    this.route.navigate(['dashboard/view/listview']);
  }

  routeBack(){
    this.location.back();
  }

}
