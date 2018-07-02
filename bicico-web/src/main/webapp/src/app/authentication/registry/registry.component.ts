import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../authentication.service';
import {User} from '../user';
import {Router} from '@angular/router';

@Component({
  selector: 'app-registry',
  templateUrl: './registry.component.html',
  styleUrls: ['./registry.component.css']
})
export class RegistryComponent implements OnInit {

  constructor(private authenticationService:AuthenticationService,private router:Router) { }
 
     onSubmit(user:User){
         user.roles=['admin'];
     this.authenticationService.register(user).subscribe(user=>{
         
         this.router.navigate(['']);
     })
 }
  ngOnInit() {
  }

}

