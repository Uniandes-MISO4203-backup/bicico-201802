import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {CookieService} from 'ngx-cookie-service';
import {AuthenticationService} from '../authentication.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private cookieService:CookieService,private authenticationService:AuthenticationService,private router: Router) { }
 logout(){
     this.authenticationService.logout();
     this.cookieService.delete('id_token');
     this.cookieService.delete('username');
     this.cookieService.delete('roles');
     this.cookieService.delete('permissions');
    
     this.router.navigate(['']);
 }
  ngOnInit() {
      this.logout();
      
  }

}
