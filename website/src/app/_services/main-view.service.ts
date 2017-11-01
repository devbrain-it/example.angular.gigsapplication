import { Injectable, ViewChild, OnInit, Type } from '@angular/core';
import $ from 'jquery';
import { GigViewModel } from '../gig-table/model/GigViewModel';
import { GigJSON } from '../login-view/model/GigJSON';
import { DialogButton } from '../core/dialog/model/DialogButton';
import { UserViewModel } from '../login-view/model/UserViewModel';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { Command } from '../core/Command';
import { GigEditComponent } from '../gig-edit-view/gig-edit.component';
import { LoginService } from './login.service';
import { DialogService } from './dialog.service';
import { GetAllGigsCommand, GigDataJsonMapper } from '../gig-edit-view/command/get-all-gigs.command';

const $GigsUpdatedCallback = new Subject<GigViewModel[]>();
const $GigEditEventState = new Subject<GigViewModel>();
const $GigSaveEventState = new Subject<GigViewModel>();
const $GigCancelEventState = new Subject<GigViewModel>();
let $GigEditComponent: GigEditComponent;
let $LoginService: LoginService;
let $DialogService: DialogService;

@Injectable()
export class MainViewService {

  constructor(
    private readonly loginService: LoginService,
    private readonly dialogService: DialogService) {

    $LoginService = loginService;
    $DialogService = dialogService;
  }

  setGigEditor(gigEditorComponent: GigEditComponent) {
    $GigEditComponent = gigEditorComponent;
  }

  get updateGigsEvent(): Observable<GigViewModel[]> {
    return $GigsUpdatedCallback.asObservable();
  }

  get editGigEvent(): Observable<GigViewModel> {
    return $GigEditEventState.asObservable();
  }

  get gigSavedEvent(): Observable<GigViewModel> {
    return $GigSaveEventState.asObservable();
  }

  get gigCancelEvent(): Observable<GigViewModel> {
    return $GigCancelEventState.asObservable();
  }

  updateGigs(user?: UserViewModel) {
    console.log('updateGigs');
    // tslint:disable-next-line:no-unused-expression
    console.log(JSON.stringify(new Error('updateGigs').stack));

    if (user == null) {
      user = $LoginService.user;
    }
    new GetAllGigsCommand()
      .then(jsonData => this.mapDataAndNotify($LoginService, $DialogService, jsonData, user))
      .execute();
  }

  public showGigEditor(model: GigViewModel) {
    $GigEditEventState.next(model);
  }

  private mapDataAndNotify(loginService: LoginService, dialogService: DialogService, gigJSONArray: GigJSON[], user?: UserViewModel) {
    console.log('mapDataAndNotify');

    // Mapping von GigJSON nach GigViewModel
    // TODO Mapping vereinfachen. GigJSON an GigViewModel implementieren
    const jsonMapper = new GigDataJsonMapper($LoginService, $DialogService);

    const isLoggedIn = user != null && user.loggedIn;
    const models: GigViewModel[] = gigJSONArray.map(jsonMapper.map);

    // Aktualisiert ob man Teilnehmen kann und ob der Benutzer am Gig Teilnimmt oder nicht
    models.forEach(gigVM => {
      gigVM.setAttendable(isLoggedIn);
      gigVM.editEvent.addListener('edit', model => this.showGigEditor(model));
      gigVM.savedEvent.addListener('saved', model => $GigSaveEventState.next(model));
      gigVM.editCancelEvent.addListener('cancel', model => $GigCancelEventState.next(model));
    });
    // generelles event melden
    $GigsUpdatedCallback.next(models);
  }
}
