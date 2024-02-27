import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PopupComponent } from '../component/popup/popup.component';

@Injectable({
  providedIn: 'root'
})
export class PopupService {

  constructor(public dialog: MatDialog) { }

  openPopup(title:String, content:String): void {
    const dialogRef = this.dialog.open(PopupComponent, {
      width: '300px',
      data: { title: title, content: content },
    });
  }
}
