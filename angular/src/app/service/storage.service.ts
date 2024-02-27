import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';


const TOKEN_KEY = 'token';


@Injectable({
  providedIn: 'root'
})

export class StorageService {

  constructor(private cookie: CookieService) { }

  clean(): boolean {
    this.cookie.deleteAll();
    return !this.isLoggedIn();
  }
  saveToken(token: any): boolean {
    this.cookie.set(TOKEN_KEY, token);
    return this.isLoggedIn();
  }

  setDateUser(type:string ,data:any){
    this.cookie.set(type, data);
  }

  getDateUser(type:string): string{
    return this.cookie.get(type);
  }
  getToken(): any {
    return this.cookie.get(TOKEN_KEY);
  }
  isLoggedIn(): boolean {
    return !!this.cookie.get(TOKEN_KEY);
  }
}
