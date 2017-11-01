import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { DialogComponent } from './core/dialog/dialog.component';
import { UserLoginComponent } from './login-view/user-login.component';
import { BubbleComponent } from './core/bubble/bubble.component';
import { GigEditComponent } from './gig-edit-view/gig-edit.component';
import { GigTableComponent } from './gig-table/gig-table.component';
import { UnattendUserFromGigCommand } from './gig-table/command/UnattendUserFromGigCommand';
import { AttendUserToGigCommand } from './gig-table/command/AttendUserToGigCommand';
import { GigViewModel } from './gig-table/model/GigViewModel';
import { UserViewModel } from './login-view/model/UserViewModel';

import { GigListItemComponent } from './gig-list-element/gig-list-item.component';
import { ImpressumComponent } from './impressum/impressum.component';
import { LoginService } from './_services/login.service';
import { MainViewService } from './_services/main-view.service';
import { BubbleService } from './_services/bubble.service';
import { DialogService } from './_services/dialog.service';

@NgModule(({
  declarations: [
    AppComponent,
    UserLoginComponent,
    DialogComponent,
    GigEditComponent,
    GigTableComponent,
    BubbleComponent,
    GigListItemComponent,
    ImpressumComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    FormsModule
  ],
  providers: [ LoginService, MainViewService, BubbleService, DialogService ],
  bootstrap: [AppComponent]
}) as any)
export class AppModule { }
