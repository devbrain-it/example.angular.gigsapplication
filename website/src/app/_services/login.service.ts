import { AppComponent } from '../app.component';
import { Injectable } from '@angular/core';
import Promise from 'ts-promise';
import { CallbackPromise } from '../core/CallbackPromise';
import { UtilsModule } from '../core/utils.module';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { UserViewModel } from '../login-view/model/UserViewModel';
import { LoginUserCommand } from '../login-view/command/LoginUserCommand';
import { UserJSON } from '../login-view/model/UserJSON';
import { SimpleUser } from '../login-view/model/SimpleUser.interface';

declare var firebase: any;

let $CurrentUser: UserViewModel;
const $LoginEventState = new Subject<UserViewModel>();
const $LogoutEventState = new Subject();
const $ShowGigEditorEventState = new Subject<boolean>();

const hashCode = obj => UtilsModule.hashCode(obj);



@Injectable()
export class LoginService {

    constructor() {
    }

    get loginEvent(): Observable<UserViewModel> {
        return $LoginEventState.asObservable();
    }

    get logoutEvent(): Observable<any> {
        return $LogoutEventState.asObservable();
    }

    get showGigEditorEvent(): Observable<boolean> {
        return $ShowGigEditorEventState.asObservable();
    }

    get user(): UserViewModel {
        return $CurrentUser;
    }

    set user(userValue: UserViewModel) {
        $CurrentUser = userValue;
        if ($CurrentUser == null) {
            $LogoutEventState.next();
        } else {
            $CurrentUser.isEditorVisibleEvent.subscribe(editorVisible => this.onEditorVisibilityChanged(editorVisible));
            $LoginEventState.next($CurrentUser);
        }
    }

    get isAuthenticatedEvent(): Observable<boolean> {
        const state = new Subject<boolean>();
        firebase.auth().onAuthStateChanged(userData => state.next(userData));
        return state.asObservable();
    }

    login(userName: string, pwClearText: string) {
        const parameters = {
            'login': userName,
            'password': `${hashCode(pwClearText)}`
        };

        // TOOD auth with "firebase"

        new LoginUserCommand()
            .then((jsonUser: UserJSON) => {
                let userModel = new UserViewModel();
                userModel.setData(jsonUser);
                if (jsonUser != null && userModel.loggedIn) {
                    userModel.userName = jsonUser.visibleName;
                    this.user = userModel;
                } else {
                    userModel = null;
                    this.user = null;
                }
            })
            .execute(parameters);
    }

    // use it!
    public logoutFirebase(): Observable<any> {
        const state = new Subject<any>();

        // firebase.auth().logout()
        //     .catch(this.onFirebaseError)
        //     .then(val => state.next(val));
        setTimeout(on => state.next(null), 1000);

        return state.asObservable();
    }

    private onEditorVisibilityChanged(editorVisible: boolean) {
        $ShowGigEditorEventState.next(editorVisible);
    }

    // use it!
    private signupFirebase(login: SimpleUser): Observable<any> {
        const state = new Subject<any>();
        state.subscribe(this.onSignUp);
        firebase.auth().signInWithEmailAndPassword(login.username, login.password)
            .catch(this.onFirebaseError)
            .then(ok => state.next(ok));

        return state.asObservable();
    }

    private onSignUp(value: any) {
        // TODO
        console.log(`SIGNUP-Result: ${value}`);
    }

    private onFirebaseError(error: any) {
        console.log(`FIREBASE-ERROR ${error}`);
    }

    // use it!
    private loginFirebase(login: SimpleUser): Observable<any> {
        const state = new Subject<any>();

        firebase.auth().loginWithEmailAndPassword(login.username, login.password)
            .catch(this.onFirebaseError)
            .then(val => state.next(val));

        return state.asObservable();
    }
}
