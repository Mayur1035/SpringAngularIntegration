import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { User } from '../user';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class UserAuthService {

  private authUrl: string;
  private userName : BehaviorSubject<string>;
  private user : User;
  private  token : any;

  constructor(private httpClient:HttpClient , private authSvc : AuthenticationService) { 
   // this.authUrl='http://localhost:8089/api/v1/auth/register/';
   this.authUrl='http://localhost:8089/api/v1/auth/';
   this.userName=new BehaviorSubject('');
   this.user = new User();
  }

  registerUser(data){
    //return this.httpClient.post(this.authUrl, data );
    return this.httpClient.post(`${this.authUrl}register`, data );
  }

  fetchUserDetails(){
    this.token = this.authSvc.getBearerToken();
    return this.httpClient.get<User>(`${this.authUrl}login/userDetails`,{
          headers : new HttpHeaders()
          .set('Authorization', `Bearer ${this.token}`)
    }).subscribe(
      fetchedUser => {
        this.user = fetchedUser;
        this.userName.next(this.user.firstName);
      },
      err =>{
        console.log(err);
      }
    );
  }

  getUserName(): Observable<string>{
    this.fetchUserDetails();
    return this.userName;
  }

}
