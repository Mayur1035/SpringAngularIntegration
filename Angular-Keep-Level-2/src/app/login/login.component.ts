import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import { RouterService } from '../services/router.service';
import { User } from '../user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  bearerKey: any;
  errorMessage: string;
  user: User;
  constructor(private authService: AuthenticationService, private routeSvc: RouterService) {
    this.user = new User();
  }

  ngOnInit() {
  }

  switchToNewUserForm() {
    this.routeSvc.routeToNewUserForm();
  }

  loginSubmit() {
    console.log(this.user);
    if (this.user.userId && this.user.userPassword) {
      this.authService.authenticateUser(this.user).subscribe(
        res => {
          (<any>document.querySelector("#login-form")).reset();
          // console.log(res);
          this.bearerKey = res['token'];
          // this.bearerKey = res;
          console.log(this.bearerKey);
          this.authService.setBearerToken(this.bearerKey);
          this.routeSvc.routeToDashboard();
        },
        err => {
          if (err.error.message) {
            this.errorMessage = err.error.message;
          } else {
            this.errorMessage = err.message;
          }
        }
      );
    } else {
      this.errorMessage = "Please provide valid inputs";
    }
  }
}
