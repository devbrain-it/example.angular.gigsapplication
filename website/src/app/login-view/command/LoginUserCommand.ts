import { Command } from '../../core/Command';
import { UserViewModel } from '../model/UserViewModel';
import { UserJSON } from '../model/UserJSON';

export class LoginUserCommand extends Command<UserJSON> {

    constructor() {
        super('http://localhost:4201/gigs/rest/user/login');
    }

    getMethod(): string {
        return 'POST';
    }
}
