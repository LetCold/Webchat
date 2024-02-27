import { AfterViewChecked, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ChatService } from 'src/app/service/chat.service';
import { FriendService } from 'src/app/service/friend.service';
import { WebsocketService } from 'src/app/service/websocket.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, AfterViewChecked {

  @ViewChild('scrollMe') private myScrollContainer!: ElementRef;

  friends: any;

  friendChat: any = {
    id: 0,
    name: "",
    imageName: ""
  };

  msg: any;

  messages: any = [];

  constructor(
    private friend: FriendService,
    private chat: ChatService,
    private webSocket: WebsocketService
  ) { }

  ngOnInit(): void {
    this.onLoad();
    this.webSocket.getMessageObservable().subscribe((data: any) => {
      const parsedData = JSON.parse(data);
      this.messages.push(parsedData);
      console.log(this.messages);
    });
  }

  async openChat(id: number, name: string, imageName: String) {
    const backgroundDefault = document.getElementById("backgroudDefault")
    const chat = document.getElementById("chat");
    if (backgroundDefault && chat) {
      backgroundDefault.classList.add("d-none");
      chat.classList.remove("d-none");
      this.friendChat.id = id;
      this.friendChat.name = name;
      this.friendChat.imageName = imageName;
    }
    try {
      this.messages = (await this.chat.getMessage(id).toPromise()).listMessage;
      console.log(this.messages);
    } catch (error) {
      console.log(error);
    }
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  private scrollToBottom(): void {
    try {
      this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
    } catch (err) { }
  }


  async onLoad() {
    try {
      this.friends = (await this.friend.getFriend().toPromise()).listFriend;
    } catch (error) {
      console.log(error);
    }
  }

  async onSubmit(id: number) {
    if (id == null || this.msg == null) return;
    try {
      const message =  {content: this.msg, receiver: id};
      const res = this.webSocket.sendMessage(JSON.stringify(message));
      this.messages.push(message);
      this.msg = ""
    } catch (error) {
      console.log(error);
    }
  }

}
