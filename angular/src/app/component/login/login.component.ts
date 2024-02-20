import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  openRegister() {
    document.getElementById('register')?.classList.add('show', 'active');
    document.getElementById('login-tab')?.classList.remove('active');
    document.getElementById('register-tab')?.classList.add('active');
    document.getElementById('login')?.classList.remove('show', 'active');
  }

  openLogin() {
    document.getElementById('register')?.classList.remove('show', 'active');
    document.getElementById('login-tab')?.classList.add('active');
    document.getElementById('register-tab')?.classList.remove('active');
    document.getElementById('login')?.classList.add('show', 'active');
  }
}
