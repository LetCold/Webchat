import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent {

  constructor(private router:Router){}
  editInfo() {
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

  logout(){
    this.router.navigate(['/'])
  }
}
