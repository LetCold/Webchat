import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private socket: WebSocket;

  private messageObservable: Observable<any>;

  constructor(private storage: StorageService) {
    this.socket = new WebSocket('ws://localhost:8080/websocket?token='+storage.getToken());

    this.messageObservable = new Observable(observer => {
      this.socket.onmessage = event => observer.next(event.data);
    });
    this.socket.onerror = function(event){
      console.log(event.type);
    }
    this.socket.onclose =  function(event){
      console.log("kết nối đã đóng");
    }
    this.socket.onopen  =  function(event){
      console.log("mở kết nối thành công")
;    }
  }

  public sendMessage(message: any): void {
    console.log(message)
    this.socket.send(message);
  }

  getMessageObservable(): Observable<any> {
    return this.messageObservable;
  }
}
