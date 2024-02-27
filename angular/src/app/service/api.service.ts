import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';

const API = "http://localhost:8080/api";
@Injectable({
  providedIn: 'root'
})
export class ApiService {
  constructor(private http: HttpClient, private storage: StorageService) { }

  postApi(url: any, data: any): any {
    const token = this.storage.getToken();
    const headers = new HttpHeaders().set("Authorization", "Bearer " + token);
    return this.http.post<any>(API + url, data, { headers });
  }

  getApi(url: string): any {
    const token = this.storage.getToken();
    const headers = new HttpHeaders().set("Authorization", "Bearer " + token);
    return this.http.get(API + url, { headers });
  }

  putApi(url: string, data: any): any {
    const token = this.storage.getToken();
    const headers = new HttpHeaders().set("Authorization", "Bearer " + token);
    return this.http.put(API + url, data, { headers });
  }

  deleteApi(url: string): any {
    const token = this.storage.getToken();
    const headers = new HttpHeaders().set("Authorization", "Bearer " + token);
    return this.http.delete(API + url, { headers });
  }

}
