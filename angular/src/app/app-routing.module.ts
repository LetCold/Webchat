import { NgModule, inject } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatComponent } from './component/chat/chat.component';
import { FriendComponent } from './component/friend/friend.component';
import { IndexComponent } from './component/index/index.component';
import { LoginComponent } from './component/login/login.component';
import { ProfileComponent } from './component/profile/profile.component';
import { GuardService } from './service/guard.service';

const routes: Routes = [
  {
    path: '',
    component: IndexComponent,
    children: [
      {
        path:"friend",
        component: FriendComponent, canActivate: [() => (inject(GuardService).checkUser())]
      },
      {
        path: 'profile',
        component: ProfileComponent,  canActivate: [() => (inject(GuardService).checkUser())]
      },
      {
        path: 'home',
        component: ChatComponent,  canActivate: [() => (inject(GuardService).checkUser())]
      },
      {
        path: '',
        component: LoginComponent, canActivate: [() => (inject(GuardService).checkAuth())]
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
