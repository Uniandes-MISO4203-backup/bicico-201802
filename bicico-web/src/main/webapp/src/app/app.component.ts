import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from './authentication/authentication.service';
import {CookieService} from 'ngx-cookie-service';
import {ActivatedRoute, Route, Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit  {
    evt:string;
    isAuthenticated:boolean;
    ngOnInit(): void {
        this.router.events.subscribe(event=>{
            for(this.evt in event){
            if(this.evt=="url"){
            if(event[this.evt]=='/' || event[this.evt]=='/register' ){    
                this.isAuthenticated=false; 
            }else{
              this.isAuthenticated=true;  
            }
            }}
        })
       
    }
   title = 'app';
constructor(private activatedRoute:ActivatedRoute, private router:Router){
    
}

}

