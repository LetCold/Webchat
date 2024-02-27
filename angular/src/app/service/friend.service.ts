import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { PopupService } from './popup.service';

@Injectable({
  providedIn: 'root'
})
export class FriendService {

  constructor(
    private api: ApiService,
    private popup: PopupService
  ) { }

  getFriend(): any {
    return this.api.getApi("/user/friend");
  }

  getFindFriend(data: any) {
    return this.api.postApi("/user/friend/find", data);
  }

  async addFriend(data: any) {
    try {
      const res = await this.api.postApi("/user/friend", data).toPromise();

      if (res.status == "success") {
        this.popup.openPopup("Lời mời kết bạn", "giửi lời mời thành công");
      } else {
        this.popup.openPopup("Lời Mời Kết bạn", "giửi lời mời thất bại");
      }
    } catch (error) {
      console.log(error);
    }
  }

  async deleteFriend(data: any) {
    try {
      const res = await this.api.deleteApi("/user/friend/" + data).toPromise();
      if (res.status == "success") {
        this.popup.openPopup("Xóa kết bạn", "Xóa kết bạn thành công");
      } else {
        this.popup.openPopup("Xóa Kết bạn", "xóa kết bạn thất bại");
      }
    } catch (error) {
      console.log(error)
    }

  }

  async submitRequestFriend(data:any){
    try{
      const res = await this.api.postApi("/user/friend/request", data).toPromise();
      if (res.status == "success") {
        this.popup.openPopup("Chấp nhận kết bạn", "Chấp nhận kết bạn thành công");
      } else {
        this.popup.openPopup("Chấp nhận kết bạn", "Chấp nhận kết bạn thất bại");
      }
    }catch(error){
      console.log(error);
    }
  }

  getRequestFriend(): any {
    return this.api.getApi("/user/friend/request");
  }

  searchFriend(data: any) {
    return this.api.postApi("/user/friend/search", data);
  }
}
