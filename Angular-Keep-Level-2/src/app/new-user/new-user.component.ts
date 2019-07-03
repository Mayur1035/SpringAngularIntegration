import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserAuthService } from '../services/user-auth.service';
import { RouterService } from '../services/router.service';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent implements OnInit {

  user: User;
  errorMessage: string;
  isUserRegistered: boolean;
  cardTitle: string;
  cardSubTitle: string;
  firstName: string;
  userId: string;

  constructor(private userSvc: UserAuthService, private routeSvc: RouterService) { }

  ngOnInit() {
    this.errorMessage = '';
    this.user = new User();
    this.isUserRegistered = false;
    this.cardTitle = 'New User Details';
    this.cardSubTitle = 'Please fill the form for Accessing Keep';
  }

  switchToLogin() {
    this.routeSvc.routeToLogin();
  }

  registerUser() {
    this.firstName = this.user.firstName;
    this.userId = this.user.userId;
    console.log(this.user);
    if(this.firstName && this.userId && this.user.userPassword){
      this.userSvc.registerUser(this.user).subscribe(
        data => {
          (<any>document.querySelector("#register-form")).reset();
          this.isUserRegistered = true;
          this.cardTitle = 'User ' + this.firstName + ' is registered';
          this.cardSubTitle = 'Registered UserId :' + this.userId;
        },
        err => {
          this.errorMessage = "Issue Occurred while Registration";
        }
      );
      this.user = new User();
      this.errorMessage = '';
    }else{
      this.errorMessage = "Provide valid details for Registration";
    }
  }

}
