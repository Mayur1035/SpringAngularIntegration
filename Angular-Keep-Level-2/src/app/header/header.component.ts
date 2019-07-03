import { Component, OnInit } from '@angular/core';
import { RouterService } from '../services/router.service';
import { UserAuthService } from '../services/user-auth.service';
import { AuthenticationService } from '../services/authentication.service';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isNoteView: boolean;
  token: any;
  userName: string;
  isLoggedIn: boolean;
  greetUser: string;
  page: string;

  constructor(private router: RouterService,
    private userSvc: UserAuthService, private authSvc: AuthenticationService,
    private route: ActivatedRoute, ngRouter: Router) {
    this.isNoteView = true;
    this.greetUser = '';
    //this.getUserName();

    ngRouter.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        // this._activatedRoute.snapshot is up to date
        this.page = route.root.firstChild.snapshot.data.page;
        console.log('page', this.page);
        if (this.page === "dashboard" || this.page === "category" || this.page === "reminder") {
          this.isLoggedIn = true;
          this.getUserName();
        } else if (this.page === "register") {
          this.isLoggedIn = false;
          this.authSvc.removeBearerToken();
          this.router.routeToNewUserForm();
        } else {
          this.logout();
        }
      }
    });
  }
  ngOnInit() {
    //this.isLoggedInUser();
    this.getUserName();
  }
  switchToListView() {
    this.isNoteView = false;
    this.router.routeToListView();
  }
  switchToCategoryView() {
    this.router.routeToCategoryDashboard();
  }
  switchToReminderView() {
    this.router.routeToReminderDashboard();
  }
  switchToHome() {
    if (this.isLoggedIn) {
      this.router.routeToDashboard();
    } else {
      this.router.routeToLogin();
    }
  }
  switchToNoteView() {
    this.isNoteView = true;
    this.router.routeToDashboard();
  }
  logout() {
    this.isLoggedIn = false;
    this.authSvc.removeBearerToken();
    this.router.routeToLogin();
  }
  getUserName() {
    this.userSvc.getUserName().subscribe(
      res => {
        this.userName = res;
        if (this.userName) {
          this.isLoggedIn = true;
          this.greetUser = 'Hello ' + this.userName;
        } else {
          this.greetUser = '';
          this.isLoggedIn = false;
        }
      }
    )
  }

  isLoggedInUser() {
    this.token = this.authSvc.getBearerToken();
    if (this.token) {
      this.isLoggedIn = true;
      this.router.routeToDashboard();
    } else {
      this.isLoggedIn = false;
      this.router.routeToLogin();
    }
  }
}
