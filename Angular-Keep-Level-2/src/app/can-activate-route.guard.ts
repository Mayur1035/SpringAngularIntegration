import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import {AuthenticationService} from './services/authentication.service';
import {RouterService} from './services/router.service';

@Injectable()
export class CanActivateRouteGuard implements CanActivate {

  constructor(private authSvc:AuthenticationService, private routerSvc:RouterService){ }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
      const booleanVal = this.authSvc.isUserAuthenticated(this.authSvc.getBearerToken());
      //console.log("authval "+booleanVal);
    return booleanVal.then((authenticated) => {
      if(!authenticated){
        this.routerSvc.routeToLogin();
      }
      return authenticated;
    }
    );
  }
}
