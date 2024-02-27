import { Injectable } from '@angular/core';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(
    private api: ApiService
  ) { }

  sendMessage(msg: string, id: number): any{

    return this.api.postApi("/user/message", {id: id,message: msg});
  }

  getMessage(id:number):any{
    return this.api.getApi("/user/message/"+id);
  }
}
