import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FriendService } from 'src/app/service/friend.service';

@Component({
  selector: 'app-friend',
  templateUrl: './friend.component.html',
  styleUrls: ['./friend.component.css']
})
export class FriendComponent implements OnInit {

  friends: any = [];

  requestFriend: any = [];

  findFriend: any = [];

  findEmail:any= [];

  constructor(private friend: FriendService, private router: Router) { }

  onRequestFriend() {
    document.getElementById('requestFriendTab')?.classList.add('show', 'active');
    document.getElementById('requestFriend')?.classList.add('active');

    document.getElementById('findFriend')?.classList.remove('active');
    document.getElementById('findFriendTab')?.classList.remove('show', 'active');
  }

  onFindFriend() {
    document.getElementById('findFriend')?.classList.add('active');
    document.getElementById('findFriendTab')?.classList.add('show', 'active');

    document.getElementById('requestFriendTab')?.classList.remove('show', 'active');
    document.getElementById('requestFriend')?.classList.remove('active');
  }

  ngOnInit(): void {
    this.onLoad();
  }

  async findSubmit(){
    await this.getFindFriend(this.findEmail);
  }

  async getFindFriend(email: String){
    try{
    this.findFriend = (await this.friend.getFindFriend({email: email}).toPromise()).listFriend;
    }catch(error){
      console.log(error);
    }
  }

  async addFriend(id :number){
    await this.friend.addFriend({idUser: id});
    this.onLoad();
  }

  async deleteFriend(id: number){
    await this.friend.deleteFriend(id);
    this.onLoad();
  }

  async submitRequestFriend(id: number){
    await this.friend.submitRequestFriend({idUser: id});
    this.onLoad();
  }


  async onLoad() {
    try {
      this.friends = (await this.friend.getFriend().toPromise()).listFriend;
      this.requestFriend = (await this.friend.getRequestFriend().toPromise()).listFriend;
    } catch (error) {
      console.log(error);
    }
  }

}
