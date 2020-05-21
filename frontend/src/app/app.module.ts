import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { HomeComponent } from './pages/home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from './service/auth-Service/auth.service';
import { AuthGuardService } from './service/auth-guard/auth-guard.service';
import { UserIdleModule } from 'angular-user-idle';
import { ErrorHandelerService } from './service/error-handel/error-handeler.service';
import { AlertComponent } from './components/alert/alert.component';
import { AlertService } from './service/alert/alert.service';
import { SignupPageComponent } from './pages/signup-page/signup-page.component';
import { CounterService } from './service/counter/counter.service';
import { Constants } from 'src/environments/constants';
import { DeliverySearchScreenComponent } from './pages/delivery-search-screen/delivery-search-screen.component';
import { DeliveryRequestService } from './service/delivery-request/delivery-request.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AutocompleteComponent } from './components/autocomplete/autocomplete.component';
import { LoaderComponent } from './components/loader/loader.component';
import { DeliveryInsertScreenComponent } from './pages/delivery-insert-screen/delivery-insert-screen.component';
import { ToastrModule } from 'ngx-toastr';
import { DialogComponent } from './components/dialog-alert/dialog.component';
import { CanDeactivateGuard } from './service/refresh-can-deactice-guard/can-deactive-guard.service';
import { DialogService } from './components/dialog-alert/dialog.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NewTokenService } from './service/new-token/new-token.service';
import { HttpRequestService } from './service/http-request/http-request.service';
import { LoggedInGuard } from './service/logged-in-can-active-guard/logged-in-guard.service';
import { DeliveryFormComponent } from './components/delivery-form/delivery-form.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { ProfilePageComponent } from './pages/profile/profile.component';
import { DialogChangePasswordComponent } from './components/dialog-change-password/dialog-change-password.component';
import { DialogRequestResetPasswordComponent } from './components/dialog-request-reset-password/dialog-request-reset-password.component';
import { ToggleButtonComponent } from './components/toggle-button/toggle-button.component';
import { DeliveryEditScreenComponent } from './pages/delivery-edit-screen/delivery-edit-screen.component';
import { StateService } from './service/state-service/state.service';
import { UsersPageComponent } from './pages/users-page/users-page.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { UsersEditPageComponent } from './pages/users-edit-page/users-edit-page.component';
import { UsersInsertPageComponent } from './pages/users-insert-page/users-insert-page.component';
import { RoleGuardService } from './service/role-guard/role-guard.service';
import { SideBarItemsComponent } from './components/side-bar-items/side-bar-items.component';
import {
  MatMenuModule, MatRadioModule, MatSlideToggleModule, MatIconModule,
  MatListModule, MatDialogModule, MatNativeDateModule, MatDatepickerModule,
  MatPaginatorModule, MatSortModule, MatTableModule, MatInputModule, MatProgressSpinnerModule,
  MatAutocompleteModule, MatDrawerContainer, MatSidenavModule, MatCheckboxModule
} from '@angular/material';
import { ModulesFormComponent } from './components/modules-form/modules-form.component';
import { ModulesSearchScreenComponent } from './pages/modules-search-screen/modules-search-screen.component';
import { ModulesInsertScreenComponent } from './pages/modules-insert-screen/modules-insert-screen.component';
import { ModulesEditScreenComponent } from './pages/modules-edit-screen/modules-edit-screen.component';
import { DialogAddModuleComponent } from './components/dialog-add-module/dialog-add-module.component';
import { DialogAddSubModuleComponent } from './components/dialog-add-sub-module/dialog-add-sub-module.component';
import { ApplScreenComponent } from './pages/appl-screen/appl-screen.component';
import { DialogApplContentComponent } from './components/dialog-appl-content/dialog-appl-content.component';
import { DatePipe } from '@angular/common';
// import { NbThemeModule, NbSidebarModule, NbMenuModule, NbLayoutModule, NbActionsModule, NbSelectModule,
//  NbIconModule, NbUserModule, NbContextMenuModule } from '@nebular/theme';
// import { LayoutService } from './service/layout/layout.service';
@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    HomeComponent,
    AlertComponent,
    SignupPageComponent,
    DeliverySearchScreenComponent,
    AutocompleteComponent,
    LoaderComponent,
    DeliveryInsertScreenComponent,
    DialogComponent,
    DeliveryFormComponent,
    ResetPasswordComponent,
    ProfilePageComponent,
    DialogChangePasswordComponent,
    DialogRequestResetPasswordComponent,
    ToggleButtonComponent,
    DeliveryEditScreenComponent,
    UsersPageComponent,
    UserFormComponent,
    UsersEditPageComponent,
    UsersInsertPageComponent,
    SideBarItemsComponent,
    ModulesFormComponent,
    ModulesSearchScreenComponent,
    ModulesInsertScreenComponent,
    ModulesEditScreenComponent,
    DialogAddModuleComponent,
    DialogAddSubModuleComponent,
    ApplScreenComponent,
    DialogApplContentComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatProgressSpinnerModule,
    MatInputModule, MatAutocompleteModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    NgbModule,
    MatListModule,
    MatIconModule,
    MatSlideToggleModule,
    MatRadioModule,
    MatMenuModule,
    MatSidenavModule,
    ToastrModule.forRoot({
      timeOut: 15000,
      positionClass: 'toast-bottom-right',
      preventDuplicates: false
    }),
    MatCheckboxModule,
    


    // NbSidebarModule.forRoot(),
    // NbMenuModule.forRoot(),
    // NbThemeModule.forRoot({ name: 'default' }),
    // NbLayoutModule,
    // NbSidebarModule,
    // NbActionsModule,
    // NbIconModule, NbSelectModule,
    // NbUserModule,
    // NbContextMenuModule,


    // 10 mints idle, timeout for logout, ping : useless
    UserIdleModule.forRoot({ idle: Constants.SESSION_TIME, timeout: 1, ping: 120 })
  ],
  exports: [MatInputModule, MatAutocompleteModule],
  entryComponents: [DialogComponent, DialogRequestResetPasswordComponent,
    DialogChangePasswordComponent, DialogAddModuleComponent, DialogAddSubModuleComponent, DialogApplContentComponent],
  providers: [DatePipe,AuthService, AuthGuardService, LoggedInGuard, CanDeactivateGuard, RoleGuardService,
    AlertService, CounterService, DeliveryRequestService, DialogService,
    HttpRequestService, ErrorHandelerService, NewTokenService, StateService, MatDrawerContainer],
  bootstrap: [AppComponent]
})
export class AppModule { }
