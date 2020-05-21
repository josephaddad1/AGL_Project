import { NgModule, } from '@angular/core';
import { Routes, RouterModule, Router } from '@angular/router';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { SignupPageComponent } from './pages/signup-page/signup-page.component';
import { HomeComponent } from './pages/home/home.component';
import { AppComponent } from './app.component';
import { AuthGuardService } from './service/auth-guard/auth-guard.service';
import { DeliverySearchScreenComponent } from './pages/delivery-search-screen/delivery-search-screen.component';
import { DeliveryInsertScreenComponent } from './pages/delivery-insert-screen/delivery-insert-screen.component';
import { CanDeactivateGuard } from './service/refresh-can-deactice-guard/can-deactive-guard.service';
import { LoggedInGuard } from './service/logged-in-can-active-guard/logged-in-guard.service';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { ProfilePageComponent } from './pages/profile/profile.component';
import { DeliveryEditScreenComponent } from './pages/delivery-edit-screen/delivery-edit-screen.component';
import { UsersPageComponent } from './pages/users-page/users-page.component';
import { UsersEditPageComponent } from './pages/users-edit-page/users-edit-page.component';
import { UsersInsertPageComponent } from './pages/users-insert-page/users-insert-page.component';
import { RoleGuardService } from './service/role-guard/role-guard.service';
import { ModulesEditScreenComponent } from './pages/modules-edit-screen/modules-edit-screen.component';
import { ModulesInsertScreenComponent } from './pages/modules-insert-screen/modules-insert-screen.component';
import { ModulesSearchScreenComponent } from './pages/modules-search-screen/modules-search-screen.component';
import { ApplScreenComponent } from './pages/appl-screen/appl-screen.component';

// import {MyApp} from './../../../frontend/src/';
// import { LocationStrategy, HashLocationStrategy } from '@angular/common';

// bootstrap(MyApp, [
//   Router,
//   {provide: LocationStrategy, useClass: HashLocationStrategy}
// ]);


const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'appcomponent', component: AppComponent, canActivate: [AuthGuardService] },
  { path: 'signup', component: SignupPageComponent },

  { path: 'appl-screen', component: ApplScreenComponent, canActivate: [AuthGuardService] },

  { path: 'delivery-search-screen', component: DeliverySearchScreenComponent, canActivate: [AuthGuardService] },
  {
    path: 'delivery-insert-screen', component: DeliveryInsertScreenComponent,
    canActivate: [AuthGuardService], canDeactivate: [CanDeactivateGuard]
  }, {
    path: 'delivery-edit-screen', component: DeliveryEditScreenComponent,
    canActivate: [AuthGuardService], canDeactivate: [CanDeactivateGuard]
  },

  {
    path: 'module-search-screen', component: ModulesSearchScreenComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'module-insert-screen', component: ModulesInsertScreenComponent,
    canActivate: [AuthGuardService], canDeactivate: [CanDeactivateGuard]
  },
  {
    path: 'module-edit-screen', component: ModulesEditScreenComponent,
    canActivate: [AuthGuardService], canDeactivate: [CanDeactivateGuard]
  },

  { path: 'home', component: HomeComponent, canActivate: [AuthGuardService] },
  { path: 'profile', component: ProfilePageComponent, canActivate: [AuthGuardService] },
  { path: 'users-page', component: UsersPageComponent, canActivate: [AuthGuardService, RoleGuardService] },
  { path: 'users-edit-page', component: UsersEditPageComponent, canActivate: [AuthGuardService, RoleGuardService] },
  { path: 'users-insert-page', component: UsersInsertPageComponent, canActivate: [AuthGuardService, RoleGuardService] },

  { path: 'login', component: LoginPageComponent, canActivate: [LoggedInGuard] },
  { path: 'reset-password', component: ResetPasswordComponent, canActivate: [LoggedInGuard] },

 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  
  
  exports: [RouterModule]
})
export class AppRoutingModule { }
