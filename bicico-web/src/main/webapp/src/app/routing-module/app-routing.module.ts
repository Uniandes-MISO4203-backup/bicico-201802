import {NgModule, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes, ActivatedRoute, Route} from '@angular/router';
import {LoginComponent} from '../authentication/login/login.component';
import {RegistryComponent} from '../authentication/registry/registry.component';
import {LogoutComponent} from '../authentication/logout/logout.component';
import {ProfileComponent} from '../authentication/profile/profile.component';

const routes: Routes = [
    {
        path: '',
        component: LoginComponent
    },
    {
        path: 'register',
        component: RegistryComponent
    },
    {
      path: 'logout',
      component: LogoutComponent  
    },
    {
        path:'logged',
        component:ProfileComponent
    }

];

@NgModule({
    imports:[
        CommonModule,
        RouterModule.forRoot(routes)
    ],
    exports: [RouterModule],
    declarations: []
})
export class AppRoutingModule {

}

