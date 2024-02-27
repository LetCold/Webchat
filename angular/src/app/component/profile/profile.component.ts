import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PopupService } from 'src/app/service/popup.service';
import { ProfileService } from 'src/app/service/profile.service';
import { StorageService } from 'src/app/service/storage.service';
import { AccountService } from '../../service/account.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {

  image: any;


  dataProfile = {
    name: "",
    email: "",
    image: "",

  }



  constructor(
    private router: Router,
    private account: AccountService,
    private storage: StorageService,
    private profile: ProfileService,
    private popup: PopupService
  ) {
  }
  ngOnInit(): void {
    this.onLoad();
  }

  @HostListener('change', ['$event'])
  onChange(event: Event) {
    const files = (event.target as HTMLInputElement).files;
    if (files && files.length > 0) {
      const file = files[0];
      if (file.type === 'image/png' || file.type === 'image/jpeg') {
        console.log(file.name);
        this.image = file
      } else {
        alert('Chỉ chấp nhận file ảnh với định dạng .png hoặc .jpg');
      }
    }
  }

  async onLoad() {
    try {
      const res: any = await this.profile.getProfile().toPromise();
      this.dataProfile.name = res.fullname;
      this.dataProfile.email = res.email;
      this.dataProfile.image = res.imageName;
    } catch (error) {
      console.log(error);
    }
  }

  async editInfo() {
    await this.profile.editInfo({ fullName: this.dataProfile.name, imageName: this.image == null ? null : this.image.name }, this.image);
    this.openFormEdit()
  }
  openFormEdit() {
    const card = document.getElementById('edit-info');
    const btn = document.getElementById('btn-edit-info');
    if (card && btn) {
      if (card.classList.contains('d-none')) {
        card.classList.remove('d-none');
        btn.classList.add('d-none');
      } else {
        btn.classList.remove('d-none');
        card.classList.add('d-none');
      }
    }
  }

  logout() {
    this.account.logout()
    this.router.navigate(['/'])
  }

  getFullName(): string {
    return this.storage.getDateUser("fullname");
  }

  getEmail(): string {
    return this.storage.getDateUser("email");
  }
}
