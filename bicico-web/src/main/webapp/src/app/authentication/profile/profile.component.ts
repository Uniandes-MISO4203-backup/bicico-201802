import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../authentication.service';
import {User} from '../user';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private cookieService:CookieService,private authenticationService:AuthenticationService) {
  this.user = new User;
    }
  user:User;
  ngOnInit() {
      this.user.roles = this.cookieService.get('roles').replace("[","").replace("]","").split(',');
      this.user.permissions = this.authenticationService.getAllPermissions();
      this.user.userName = this.cookieService.get('username');       
  }

}
