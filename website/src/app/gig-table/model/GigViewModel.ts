import { ViewChild } from '@angular/core';
import { DialogButton } from '../../core/dialog/model/DialogButton';
import { UnattendUserFromGigCommand } from '../command/UnattendUserFromGigCommand';
import { AttendUserToGigCommand } from '../command/AttendUserToGigCommand';
import { Observable } from 'rxjs/Observable';
import { EventEmitter } from 'events';
import { GigJSON } from '../../login-view/model/GigJSON';
import { LoginService } from '../../_services/login.service';
import { DialogService } from '../../_services/dialog.service';

const TEXT_TO_ATTEND = 'Teilnehmen';
const TEXT_UNATTEND = 'Teilnahme zurückziehen';
const TEXT_DISABLED_TOOLTIP = 'Teilnahme nur für angemeldete Benutzer möglich';

let $LoginService: LoginService;
let $DialogService: DialogService;

export class GigViewModel {
    id: number;
    date: string;
    time: string;
    genre: string;
    artist: string;
    artistId: number;
    city: string;
    attended: boolean;
    buttonTitle: string;
    disabled: boolean;
    tooltip: string;
    private readonly editEventEmitter: EventEmitter;
    private readonly savedEventEmitter: EventEmitter;
    private readonly editCancelEventEmitter: EventEmitter;

    constructor(gig: GigJSON,
        initAttended: boolean,
        dialogService: DialogService,
        loginService: LoginService) {

        $DialogService = dialogService;
        $LoginService = loginService;
        this.editEventEmitter = new EventEmitter();
        this.savedEventEmitter = new EventEmitter();
        this.editCancelEventEmitter = new EventEmitter();

        if (gig != null) {
            this.id = gig.id;
            this.date = gig.date;
            this.time = gig.time;
            this.genre = gig.genre;
            this.artist = gig.artist;
            this.city = gig.city;
        } else {
            this.id = 0;
            this.date = '';
            this.time = '';
            this.genre = '';
            this.artist = '';
            this.city = '';
        }
        this.setAttendState(initAttended);
    }

    public get editEvent(): EventEmitter {
        return this.editEventEmitter;
    }

    public get editCancelEvent(): EventEmitter {
        return this.editCancelEventEmitter;
    }

    public get savedEvent(): EventEmitter {
        return this.savedEventEmitter;
    }

    get isLoggedIn(): boolean {
        const localUser = $LoginService.user;
        return localUser != null && localUser.loggedIn;
    }

    get isModerator(): boolean {
        const GIG_MODERATOR_ID = 2; // Database
        const localUser = $LoginService.user;
        return localUser != null && localUser.roleId === GIG_MODERATOR_ID;
    }

    setAttendState(attended: boolean) {
        this.attended = attended;
        this.buttonTitle = this.getToggleAttendButtonText();
    }

    setAttendable(attendable: boolean) {
        this.disabled = !attendable;
        this.tooltip = this.disabled ? TEXT_DISABLED_TOOLTIP : null;
    }

    getToggleAttendButtonText(): string {
        return this.attended ? TEXT_UNATTEND : TEXT_TO_ATTEND;
    }

    toggleAttendState() {
        const gigId = this.id;
        const userId = $LoginService.user.id;

        if (!this.attended) {
            this.attendUserToGig(gigId, userId);
        } else {
            const okButton = new DialogButton(() => this.unattendUserFromGig(gigId, userId));
            okButton.title = 'Ja';

            // nur schließen des Dialogs
            const cancelButton = new DialogButton(() => { });
            cancelButton.title = 'Abbrechen';

            const buttons: DialogButton[] = [
                cancelButton,
                okButton
            ];

            $DialogService
                .showDialog('Teilnahme zurückziehen',
                'Sind Sie sicher, die Teilnahme zurück zu ziehen? Eine erneute Teilnahme ist vielleicht nicht mehr möglich.',
                buttons,
                500);
        }
    }

    onEditClicked() {
        this.editEventEmitter.emit('edit', this);
    }

    onSavedClicked() {
        this.savedEventEmitter.emit('saved', this);
    }

    onCancelClicked() {
        this.editCancelEventEmitter.emit('cancel', this);
    }

    private unattendUserFromGig(gigId: number, userId: number) {
        new UnattendUserFromGigCommand(gigId, userId)
            .then(response => {
                if (true === response) {
                    this.setAttendState(false);
                } else {
                    $DialogService.showDialog('Fehler', response);
                }
            })
            .execute();
    }

    private attendUserToGig(gigId: number, userId: number) {
        const me = this;
        new AttendUserToGigCommand(gigId, userId)
            .then(response => {
                if (true === response) {
                    me.setAttendState(true);
                } else {
                    $DialogService.showDialog('Fehler', response);
                }
            })
            .execute();
    }
}
