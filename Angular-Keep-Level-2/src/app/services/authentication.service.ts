import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthenticationService {

  private authUrl: string;

  constructor( private http:HttpClient) { 
   // this.authUrl='http://localhost:3000/auth/v1/';
    this.authUrl='http://localhost:8089/api/v1/auth/login/';
  }

  authenticateUser(data){
    return this.http.post(this.authUrl, data );
  }

  setBearerToken(token){
    localStorage.setItem('bearerToken', token);
  }

  getBearerToken(){
    return localStorage.getItem('bearerToken');
  }

  removeBearerToken(){
    return localStorage.removeItem('bearerToken');
  }

  isUserAuthenticated(token): Promise<boolean>{
    return this.http.post(
      `${this.authUrl}isAuthenticated`, {} , {
        headers : new HttpHeaders().set('Authorization' , `Bearer ${token}`)
      }
    ).map((res) => res['authenticated']).toPromise();
  }

}
