import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import {AppRoutingModule} from './routing-module/app-routing.module';
import {FormsModule} from '@angular/forms';
import { AuthenticationModule } from './authentication/authentication.module';
import {LogoutComponent} from './authentication/logout/logout.component';

@NgModule({
  declarations: [
    AppComponent,
    LogoutComponent   
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    AuthenticationModule,
    
  ],
  
  bootstrap: [AppComponent]
})
export class AppModule { }

