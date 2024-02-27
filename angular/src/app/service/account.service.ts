import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from './api.service';
import { PopupService } from './popup.service';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  constructor(
    private api: ApiService,
    private storage: StorageService,
    private router: Router,
    private popup: PopupService
  ) { }

  async register(data: any) {
    try {
      const res: any = await this.api.postApi('/auth/register', data).toPromise();
      if (res.token !== null) {
        this.storage.setDateUser("fullname", data.fullName);
        this.storage.setDateUser("email", data.email);
        const callBack = this.storage.saveToken(res.token);
        if (callBack) {
          this.router.navigate(['/home']);
        } else {
          this.popup.openPopup('Đăng nhập Thất Bại 2', 'vui lòng thử lại sau');
        }
      } else {
        this.popup.openPopup('Đăng kí Thất Bại 1', 'vui lòng thử lại sau');
      }
    } catch (error) {
      console.log(error);
    }
  }

  async login(data: any) {
    try {
      const res: any = await this.api.postApi('/auth/login', data).toPromise();
      if (res.token != null) {
        this.storage.setDateUser("fullname", res.fullname);
        this.storage.setDateUser("email", data.email);
        const callBack = this.storage.saveToken(res.token);
        if (callBack) {
          this.router.navigate(['/home']);
        } else {
          this.popup.openPopup('Đăng nhập Thất Bại', 'vui lòng thử lại sau');
        }
      } else {
        this.popup.openPopup('Đăng nhập Thất Bại', 'vui lòng thử lại sau');
      }
    } catch (error) {
      console.log(error);
    }
  }

  async logout() {
    try {
      const res: any = await this.api.getApi("/auth/logout").toPromise();
      if (res.status == 'success') {
        const callBack = this.storage.clean();
        if (callBack) this.router.navigate(['/']);
      }
    } catch (error) {
      console.log(error);
    }
  }
}
