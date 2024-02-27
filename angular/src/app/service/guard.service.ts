import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from './account.service';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class GuardService {

  constructor(private storage: StorageService, private router: Router, private account: AccountService) { }

  checkUser() {
    if (!this.storage.isLoggedIn()) {
      this.router.navigate(['/']);
    }
  }

  checkAuth() {
    if (this.storage.isLoggedIn()) {
      this.router.navigate(['/home']);
    }
  }
}
