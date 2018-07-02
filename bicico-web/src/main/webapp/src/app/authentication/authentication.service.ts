import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {User} from './user';
import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import 'rxjs/add/operator/concatMap';
import 'rxjs/add/observable/combineLatest';
import {CookieService} from 'ngx-cookie-service';

@Injectable()
export class AuthenticationService {
    user: User;
    baseUrl: string;
    str:any;
    permissions:string[];
    constructor(private http: HttpClient, private _router: Router, private cookieService: CookieService) {
        this.baseUrl = environment.apiURL;
        this.permissions=[];
    }

    login(user: User): Observable<User> {
        const httpObservable: Observable<HttpResponse<User>> = this.http.post<User>(this.baseUrl + '/users/login', user, {observe: 'response'});

        const loginObservable = httpObservable.concatMap<HttpResponse<User>, User>(httpResponse => {
            const token = httpResponse.headers.get('id_token');
            const user = httpResponse.body;
            user.id_token = token;
            return of(user);
        });

        return loginObservable;
    }
   
    register(user: User): Observable<User> {
        const httpObservable: Observable<HttpResponse<User>> = this.http.post<User>(this.baseUrl + '/users/register', user, {observe: 'response'});
        const registerObservable = httpObservable.concatMap<HttpResponse<User>, User>(httpResponse => {
            const user = httpResponse.body;
            return of(user);
        });
        return registerObservable;
    }
    userAuthenticated(): Observable<User> {
        const httpObservable: Observable<HttpResponse<User>> = this.http.get<User>(this.baseUrl + '/users/me', {observe: 'response'});
        const meObservable = httpObservable.concatMap<HttpResponse<User>, User>(httpResponse => {  
            if (httpResponse.body!==null){    
            this.cookieService.set('permissions', httpResponse.body.permissions.toString());
            this.cookieService.set('roles', httpResponse.body.roles.toString());}
            const user = httpResponse.body;
            return of(user);    
        });
        return meObservable;
    }
    logout():void {
       this.http.get(this.baseUrl + '/users/logout');
      
}
    getPermissions(entity:string):string[]{
        const permissions = this.cookieService.get("permissions").split(',');
        
            for (this.str in permissions ) {
                
                if (permissions[this.str].indexOf(entity)>-1)
                this.permissions.push(permissions[this.str].replace("[", "").replace("]", "").split(":")[0].replace('"',''));
           
      }
        return this.permissions;
        
    }
getAllPermissions():string[]{
        const permissions = this.cookieService.get("permissions").split(',');
        for (this.str in permissions ) {
                this.permissions.push(permissions[this.str].replace("[", "").replace("]", "").replace('"',"").replace('"',""));
      }
        return this.permissions;
    }
}
