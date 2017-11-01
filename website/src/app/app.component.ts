import { Component, Injectable, ViewChild, ElementRef, OnInit, Inject, Input } from '@angular/core';
import { TemplateRef, AfterViewChecked, ContentChild } from '@angular/core';
import $ from 'jquery';
import { UserViewModel } from './login-view/model/UserViewModel';
import { BubbleComponent } from './core/bubble/bubble.component';
import { DialogComponent } from './core/dialog/dialog.component';
import { GigTableComponent } from './gig-table/gig-table.component';
import { UserLoginComponent } from './login-view/user-login.component';
import { GigEditComponent } from './gig-edit-view/gig-edit.component';
import { GigViewModel } from './gig-table/model/GigViewModel';
import { LoginState } from './login-view/model/LoginState';
import { UpdateAllGigsWithUserInformationsCommand } from './gig-table/command/UpdateAllGigsWithUserInformationsCommand';
import { Type } from '@angular/compiler/src/output/output_ast';
import { MainViewState } from './gig-table/model/main-view-state.enum';
import { LoginService } from './_services/login.service';
import { MainViewService } from './_services/main-view.service';
import { BubbleService } from './_services/bubble.service';
import { DialogService } from './_services/dialog.service';
import { UserGigJSON } from './login-view/model/gig-user-info.model';

const TEXT_REGISTER_DEFAULT = 'Registrieren ...';
const TEXT_REGISTER_SUBMIT = 'Registrieren';
const TEXT_LOG_OUT = 'Abmelden';
const TEXT_EDIT_PROFILE = 'Profil ...';
const TEXT_LOGIN = 'Anmelden';

let TABLE_TEMPLATE: TemplateRef<GigTableComponent>;
let GIG_EDIT_TEMPLATE: TemplateRef<GigEditComponent>;
let $LoginService: LoginService;
let gTableGigs: GigViewModel[] = [];

/**
 * Master Controller
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [LoginService, MainViewService, BubbleService, DialogService]
})
export class AppComponent implements OnInit {
  title = 'Gigs Application';
  mainViewState = MainViewState;
  mainViewId: MainViewState = MainViewState.GIG_TABLE;

  @ViewChild('defaultTemplate') TABLE_TEMPLATE: TemplateRef<any>;
  @ViewChild('gigEditTemplate') GIG_EDIT_TEMPLATE: TemplateRef<any>;
  @Input() currentTemplate: TemplateRef<any>;
  @Input() templateContext: any;

  // So werden andere Component-Klassen referenziert! :-) Meilenstein! @ViewChild([Name der anderen Component-Klasse)])
  @ViewChild(BubbleComponent) private bubbleMessage: BubbleComponent;
  @ViewChild(DialogComponent) private dialogComponent: DialogComponent;
  @ViewChild(GigTableComponent) private gigTableView: GigTableComponent;
  @ViewChild(UserLoginComponent) private loginView: UserLoginComponent;
  @ContentChild(GigEditComponent, { read: GigEditComponent }) private gigEditor: GigEditComponent;
  @ViewChild(AppComponent) private self: AppComponent;

  // tslint:disable-next-line:max-line-length
  constructor(
    private readonly bubbleService: BubbleService,
    public readonly loginService: LoginService,
    private readonly mainViewService: MainViewService,
    private readonly dialogService: DialogService) {

    console.log('<init> app');

    $LoginService = loginService;

    this.loginService.loginEvent.subscribe(user => this.onLogin(user));
    this.loginService.logoutEvent.subscribe(() => this.onLogout());
    this.loginService.isAuthenticatedEvent.subscribe(authenticated => this.onLoginStateChanged(authenticated));

    this.mainViewService.updateGigsEvent.subscribe((gigs: GigViewModel[]) => this.updateUiGigs(gigs));
    this.mainViewService.editGigEvent.subscribe((gig: GigViewModel) => this.showGigEditor(gig));
    this.mainViewService.gigSavedEvent.subscribe((gig: GigViewModel) => this.showGigTable(gTableGigs, true));
    this.mainViewService.gigCancelEvent.subscribe((gig: GigViewModel) => this.showGigTable(gTableGigs, true));

    this.mainViewService.updateGigs();
  }

  showTemplate(template: TemplateRef<any>, context: any) {
    this.currentTemplate = template;
    this.templateContext = context;
  }

  ngOnInit() {
    TABLE_TEMPLATE = this.TABLE_TEMPLATE;
    GIG_EDIT_TEMPLATE = this.GIG_EDIT_TEMPLATE;
    this.setContent(MainViewState.GIG_TABLE, [], false);

    // this.bubble befindet sich in app.compontent.html
    // es soll überall zur Verfügung stehen, also wird es als
    // component an bubbleService gegeben
    this.bubbleService.setBubbleComponent(this.bubbleMessage);

    // this.dialog befindet sich in app.compontent.html
    // es soll überall zur Verfügung stehen, also wird es als
    // component an dialogService gegeben
    this.dialogService.setDialogComponent(this.dialogComponent);
  }

  /**
   * Wendet die mitgelieferten Gig-IDs auf die sichtbaren Gigs an
   * @param gigs Gigs, die angezeigt werden "sollen"
   * @param attentInfos GigIds zu Gigs, die als "nimmt teil" angezeigt werden sollen
   */
  public updateTableGigs(gigs: GigViewModel[], attentInfos: UserGigJSON[]) {
    // TODO - userGigdIdArray muss auf eine andere ID umgestellt werden
    // derzeit
    //       * wird verwendet um als "nimmt teil" angezeigt zu werden
    // gig (gig_id, künstler (artist_id))
    // Problem: das ist nicht genau genug!
    // SOLL
    // gig und künstler_id
    // Weil der Gast an einem Künstler-Auftritt teilnimmt

    if (this.mainViewId !== MainViewState.GIG_TABLE) {
      this.showGigTable(gigs);
    }

    gigs.forEach(c => c.setAttendState(false));
    for (const attendInfo of attentInfos) {
      for (const artisId of attendInfo.artistIds) {
        for (const gigComp of gigs) {
          gigComp.setAttendState(attendInfo.gigId === gigComp.id && artisId === gigComp.artistId);
        }
      }
    }
    // rendering
    this.templateContext = gigs;
  }

  private onLoginStateChanged(authenticated?: boolean) {
    if (authenticated == null || authenticated === false) {
      console.log('LOGOUT');
    } else {
      console.log('LOGIN');
    }
  }

  private onLogin(user: UserViewModel) {
    console.log('onLogin');
    this.mainViewService.updateGigs();

    if (this.loginService.user != null) {
      this.loginView.viewId = LoginState.LOGGED_IN;
      // nicht zu lange anzeigen
      this.bubbleService.showBubble(800, 'Willkommen zurück!');
    } else {
      this.bubbleService.showBubble(1200, 'Anmeldung fehlgeschlagen');
    }
  }

  private onLogout() {
    console.log('onLogout');

    this.mainViewService.updateGigs();
  }

  private updateUiGigs(gigsData: GigViewModel[]) {
    const localUser = $LoginService.user;
    if (localUser == null || false === localUser.loggedIn) {
      this.updateTableGigs(gigsData, []);
    } else {
      // alle Gigs vom angemeldeten "user" (Benutzer)
      new UpdateAllGigsWithUserInformationsCommand(localUser.id, gigsData)
        .then((userGigIdArray: UserGigJSON[]) => {
          if (userGigIdArray != null) {
            this.updateTableGigs(gigsData, userGigIdArray);
            this.bubbleService.showBubble(900, `Sie nehmen an ${userGigIdArray.length} Gig teil :-)`);
          } else {
            this.bubbleService.showBubble(900, 'Sie nehmen bislang an keinem Gig teil :-(');
          }
        })
        .execute();
    }
  }

  /**
   * CONTENT wird Gig-Tabelle
   */
  private showGigTable(gigs: GigViewModel[], tableUpdateGigs: boolean = true) {
    this.setContent(MainViewState.GIG_TABLE, gigs, tableUpdateGigs);
  }

  /**
   * CONTENT wird Gig-Editor
   */
  private showGigEditor(gig: GigViewModel) {
    // this.templateContext = gigs <--- entspricht dem Model des aktuellen Templatetyps
    // bei der Tabelle ist es "gigs"
    gTableGigs = <GigViewModel[]>this.templateContext;

    this.showTemplate(GIG_EDIT_TEMPLATE, gig);
  }

  /**
   * Wechselt den CONTENT
   */
  private setContent(mainViewState: MainViewState, data: any, tableUpdateGigs: boolean = true) {
    console.log(`INFO - SHOW CONTENT {mainViewState}`);
    this.mainViewId = mainViewState;
    if ($LoginService.user != null) {
      $LoginService.user.isEditorVisible = this.mainViewId === MainViewState.GIG_EDITOR;
    }

    switch (mainViewState) {
      case MainViewState.GIG_EDITOR:
        const gig: GigViewModel = data;
        this.showTemplate(GIG_EDIT_TEMPLATE, gig);
        break;
      default:
      case MainViewState.GIG_TABLE:
        const gigs: GigViewModel[] = data;
        this.showTemplate(TABLE_TEMPLATE, gigs);

        if (tableUpdateGigs) {
          this.mainViewService.updateGigs();
        }
        break;
    }
  }
}
