import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { AuthenticationService } from './authentication.service';
import {FormsModule} from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { RegistryComponent } from './registry/registry.component';
import {ProfileComponent} from './profile/profile.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule
  ],
  declarations: [LoginComponent,  RegistryComponent,ProfileComponent],
  providers: [AuthenticationService,CookieService]
})
export class AuthenticationModule { }
