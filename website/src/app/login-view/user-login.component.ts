import { Component, ViewChild, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { OperatorType } from './model/OperatorType';
import { UpdateUserRolesCommand } from './command/UpdateUserRolesCommand';
import { ValidationResult } from './model/ValidationResult';
import { LoginState } from './model/LoginState';
import { RegisterNewUserCommand } from './command/RegisterNewUserCommand';
import { UpdateUserProfileCommand } from './command/UpdateUserProfileCommand';
import { UserViewModel } from './model/UserViewModel';
import { RoleJSON } from './model/RoleJSON';
import { BubbleComponent } from '../core/bubble/bubble.component';
import { DialogComponent } from '../core/dialog/dialog.component';
import { UtilsModule } from '../core/utils.module';
import { LoginService } from '../_services/login.service';
import { MainViewService } from '../_services/main-view.service';
import { DialogService } from '../_services/dialog.service';
import { BubbleService } from '../_services/bubble.service';
import { DialogButton } from '../core/dialog/model/DialogButton';

const hashCode = obj => UtilsModule.hashCode(obj);
const joinStrings = (list = [], format: Function) => UtilsModule.joinStrings(list, format);
let $LoginService: LoginService;
const MODERATOR_BUTTON_TEXT_SHOW_GIG_EDITOR = 'Gig-Editor';
const MODERATOR_BUTTON_TEXT_SHOW_GIG_TABLE = 'Alle Gigs';

@Component({
    // tslint:disable-next-line:component-selector
    selector: 'user-login',
    templateUrl: './user-login.component.html',
    styleUrls: ['./../app.component.css'],
    providers: [LoginService, MainViewService, DialogService, BubbleService]
})
export class UserLoginComponent implements OnInit {
    title = 'Login';
    userName: string = null;
    password: string = null;
    passwordrepeat: string = null;
    registerButtonText = 'Registrieren ...';
    loginButtonText = 'Anmelden';
    cancelButtonText = 'Abbrechen';
    loginDescription: string;
    toggleSpeedMillis = 600;
    operatortypes: OperatorType[];
    selectedOperatorType: OperatorType;
    disabledUserName = false;

    loginState = LoginState;
    viewId = LoginState.LOGIN;
    moderatorButtonText: string;

    constructor(
        private loginService: LoginService,
        private mainViewService: MainViewService,
        private dialogService: DialogService,
        private bubbleService: BubbleService) {

        $LoginService = loginService;
        $LoginService.showGigEditorEvent.subscribe(shown => this.onShowGigEditorChanged(shown));
        this.onShowGigEditorChanged(false);
    }

    ngOnInit() {
        this.updateUserRoles();
    }

    getVisibleUserName(): string {
        return this.userName;
    }

    get isModerator(): boolean {
        return $LoginService.user.roleId === 2;
    }

    updateUserRoles() {
        new UpdateUserRolesCommand()
            .then((roles: RoleJSON[]) => {
                const result: OperatorType[] = [];
                let defaultRole: OperatorType = null;
                let i = 0;
                for (const role of roles) {
                    const uiRole = new OperatorType(role.id, role.roleName, role.isDefault);
                    uiRole.description = role.description;
                    if (defaultRole == null) {
                        const localUser = this.loginService.user;
                        if (localUser != null) {
                            if (localUser.roleId === role.id) {
                                defaultRole = uiRole;
                            }
                        } else if (uiRole.select) {
                            defaultRole = uiRole;
                        }
                    }
                    result[i++] = uiRole;
                }

                this.operatortypes = result;
                this.setDefaultRole(defaultRole);
            })
            .execute();
    }

    getUserDisplayedName() {
        const localUser = this.loginService.user;
        return localUser == null ? '' : localUser.userName;
    }

    onRegisterOperatorTypeChanged(value: string) {
        this.loginDescription = '';
        for (const item of this.operatortypes) {
            item.select = item.value === value;
            if (item.value === value) {
                this.setDefaultRole(item);
            }
        }
    }

    /**
     * Bei Klick auf "Registrieren ..."
     * - Registrierung validieren
     */
    onRegisterClicked() {
        // show
        this.disabledUserName = false;
        if (this.viewId !== LoginState.REGISTER) {
            const localUser = this.loginService.user;
            if (localUser != null && localUser.loggedIn) {
                this.onLogoutClicked();
            } else {
                this.viewId = LoginState.REGISTER;
            }
        } else {
            this.performRegister();
        }
    }

    onRegisterCancelClicked() {
        this.disabledUserName = false;
        this.viewId = LoginState.LOGIN;
    }

    /**
     * Startet eine Validierung und die Registrierung eines neuen Benutzers
     */
    performRegister() {
        // validate and hide
        const login: string = this.userName;
        const pwHash: string = this.password;
        const pwSecure: string = this.passwordrepeat;

        const result = this.validateNewUserName(login);
        result.append(this.validateNewPassword(pwHash, pwSecure));

        if (!this.showValidationErrors(result)) {
            const role: number = this.selectedOperatorType.id;
            this.doRegisterNewUser(login, pwHash, role, this.getVisibleUserName());
        }
    }

    /**
     * Zeigt die Validierungsfehler an und meldet in dem Fall von Fehlern "true" zurück.
     */
    showValidationErrors(result: ValidationResult): boolean {
        const hasErrors: boolean = result != null && result.errors.length > 0;
        if (result != null && hasErrors) {
            const errors: string = joinStrings(result.errors, txt => `- ${txt}`);
            this.bubbleService.showBubble(1200, errors);
        }
        return hasErrors;
    }

    validateNewUserName(loginText: string): ValidationResult {
        if (UtilsModule.isStringNullOrEmpty(loginText)) {
            // empty
            return new ValidationResult(['Benutzername darf nicht leer sein']);
        }

        loginText = loginText.trim();
        const num = 3;
        if (loginText.length < num) {
            // wrong length
            return new ValidationResult([`Benutzername muss mindestens ${num} Zeichen haben`]);
        } else if (loginText.indexOf(' ') > -1) {
            return new ValidationResult(['Der Benutzername darf keine Leerzeichen beinhalten']);
        }

        return new ValidationResult([]);
    }

    validateNewPassword(pwHash, pwSecure): ValidationResult {
        if (UtilsModule.isStringNullOrEmpty(pwHash)) {
            // no pw
            return new ValidationResult(['Das Passwort ist leer']);
        } else {
            pwHash = pwHash.trim();
            if (UtilsModule.isStringNullOrEmpty(pwSecure)) {
                // no secure
                return new ValidationResult(['Das Probe-Passwort ist leer']);
            } else {
                pwSecure = pwSecure.trim();
                if (pwSecure.length !== pwHash.length) {
                    // wrong length
                    return new ValidationResult(['Die Passwörter haben ungleiche Länge']);
                } else if (pwSecure !== pwHash) {
                    // wrong content
                    return new ValidationResult(['Die Passwörter sind ungleich']);
                }
            }
        }
        return new ValidationResult([]);
    }

    doRegisterNewUser(loginUserName: string, pwUnencoded: string, role: number, userName) {
        const parameters = {
            login: loginUserName,
            userName: userName,
            password: `${hashCode(pwUnencoded)}`,
            role: role
        };
        new RegisterNewUserCommand()
            .then(userIdOrError => {
                if (UtilsModule.isInt(userIdOrError)) {
                    this.bubbleService.showBubble(1200, 'Willkommen!');
                } else {
                    const okButton: DialogButton = new DialogButton(() => { });
                    okButton.title = 'OK';
                    const buttons: DialogButton[] = [
                        okButton
                    ];
                    this.dialogService.showDialog('Fehler', `Fehler: ${userIdOrError}`, buttons);
                }
            })
            .execute(parameters);
    }

    onProfileClicked() {
        this.viewId = LoginState.PROFILE;
        this.password = '';
        this.passwordrepeat = '';
        this.disabledUserName = true;
        for (const operatorType of this.operatortypes) {
            operatorType.select = operatorType.id === this.loginService.user.roleId;
            if (operatorType.select) {
                this.setDefaultRole(operatorType);
            }
        }
    }

    onLoginClicked() {
        this.disabledUserName = false;
        this.loginService.login(this.userName, this.password);
    }

    onProfileCancelClicked() {
        this.disabledUserName = false;
        this.viewId = LoginState.LOGGED_IN;
    }

    onProfileChangeClickedSubmit() {
        this.disabledUserName = false;
        this.viewId = LoginState.LOGGED_IN;
        const pw = `${hashCode(this.password)}`;
        const pwr = `${hashCode(this.passwordrepeat)}`;
        const result = this.validateNewPassword(pw, pwr);

        if ('0' === pw || !this.showValidationErrors(result)) {
            const parameters = {
                login: this.userName,
                password: pw,
                role: this.selectedOperatorType.id,
                userName: this.getVisibleUserName()
            };
            new UpdateUserProfileCommand()
                .then(data => {
                    if (UtilsModule.isString(result) && data !== 'true') {
                        this.bubbleService.showBubble(1200, `Fehler: ${data}`);
                    } else {
                        this.onLogoutClicked();
                        this.bubbleService.showBubble(1200, 'Profil aktualisiert. Sie sollten sich erneut anmelden');
                    }
                })
                .execute(parameters);
        }
    }

    onDeleteUserClicked() {

    }

    onLogoutClicked() {
        this.viewId = LoginState.LOGIN;
        this.disabledUserName = false;
        this.loginService.user = null;
        this.userName = '';
        this.password = '';
        this.passwordrepeat = '';
        this.mainViewService.updateGigs();
    }

    setDefaultRole(defaultRole: OperatorType) {
        this.loginDescription = defaultRole == null ? '' : defaultRole.description;
        this.selectedOperatorType = defaultRole;
    }

    onShowGigEditorClicked() {
        // umkehren
        if ($LoginService.user.isEditorVisible) {
            this.mainViewService.updateGigs();
            $LoginService.user.isEditorVisible = false;
        } else {
            this.mainViewService.showGigEditor(null);
            $LoginService.user.isEditorVisible = true;
        }
    }

    /**
     * Wird gemeldet, wenn am Benutzer der Editor umgeschaltet wird
     */
    private onShowGigEditorChanged(visible: boolean) {
        if (visible) {
            this.moderatorButtonText = MODERATOR_BUTTON_TEXT_SHOW_GIG_TABLE;
        } else {
            this.moderatorButtonText = MODERATOR_BUTTON_TEXT_SHOW_GIG_EDITOR;
        }
    }
}
