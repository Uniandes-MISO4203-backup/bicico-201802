import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {AuthenticationService} from '../authentication.service';
import {User} from '../user';
import { CookieService } from 'ngx-cookie-service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
user:User;
  constructor(private router:Router,private authenticationService:AuthenticationService, private cookieService: CookieService) { }
 
onSubmit(user:User){
    
    this.authenticationService.login(user).subscribe(user=> {
      this.cookieService.set('id_token', user.id_token);
      this.cookieService.set('username', user.userName);
      this.cookieService.set('permissions', JSON.stringify(user.permissions));
      this.cookieService.set('roles', JSON.stringify(user.roles));
      this.router.navigate(['logged']);
    });
    //    this.cookieService.set('id_token',)
   
    
}
  ngOnInit() {
      
  }

}
