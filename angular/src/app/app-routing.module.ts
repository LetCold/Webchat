import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatComponent } from './component/chat/chat.component';
import { LoginComponent } from './component/login/login.component';
import { IndexComponent } from './component/index/index.component';
import { ProfileComponent } from './component/profile/profile.component';
import { FriendComponent } from './component/friend/friend.component';

const routes: Routes = [
  {
    path: '',
    component: IndexComponent,
    children: [
      {
        path:"friend",
        component: FriendComponent
      },
      {
        path: 'profile',
        component: ProfileComponent,
      },
      {
        path: 'home',
        component: ChatComponent,
      },
      {
        path: '',
        component: LoginComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
