import { UserJSON } from './UserJSON';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';

export class UserViewModel {

    loggedIn: boolean;
    login: string;
    userName = '';
    id: number;
    roleId: number;
    private editorVisible: boolean;

    private readonly editorVisibleState: Subject<boolean>;

    constructor() {
        this.editorVisible = false;
        this.editorVisibleState = new Subject<boolean>();
    }

    get isEditorVisibleEvent(): Observable<boolean> {
        return this.editorVisibleState.asObservable();
    }

    get isEditorVisible(): boolean {
        return this.editorVisible;
    }

    set isEditorVisible(visible: boolean) {
        this.editorVisible = visible;
        this.editorVisibleState.next(visible);
    }

    setData(jsonUser: UserJSON) {
        this.id = -1;
        if (jsonUser != null) {
            this.login = jsonUser.login;
            this.loggedIn = jsonUser.id > 0;
            this.id = jsonUser.id;
            this.roleId = jsonUser.roleId;
            this.userName = jsonUser.visibleName;
        }
    }
}
