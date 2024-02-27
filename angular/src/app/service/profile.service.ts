import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { ApiService } from './api.service';
import { PopupService } from './popup.service';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private api: ApiService, private popup: PopupService) { }


  getProfile(){
    return this.api.getApi("/user/profile");
  }


  async editInfo(data:any, image: any) {
    try {
      const res: any = await this.api.putApi("/user/profile", data).toPromise();
      console.log(res);
      if (res.status == "success") {
        if(image == null){
          this.popup.openPopup("Thông tin", "Thay Đổi thông tin thành công")
          return;
        }
        const res: any = await this.uploadFile(image).toPromise();
        if (res.status == "success") {
          const avatar = document.getElementById("avatar");
          this.popup.openPopup("Thông tin", "Thay Đổi thông tin thành công")

        } else {
          this.popup.openPopup("Thông tin", "Upload ảnh thất bại")
        }
      } else {
        this.popup.openPopup("Thông tin", "Thay đổi tên Thất bại")
      }
    } catch (error) {
      console.log(error)
    }
  }


  uploadFile(file: File): any {
    if (!file) {
      const errorMessage = "Vui lòng upload file";
      return of({ error: errorMessage });
    }

    if (file.size <= 0) {
      const errorMessage = "file rỗng";
      return of({ error: errorMessage });
    }

    const formData: FormData = new FormData();
    formData.append('file', file, file.name);

    return this.api.postApi("/user/profile/upload", formData);
  }
}
