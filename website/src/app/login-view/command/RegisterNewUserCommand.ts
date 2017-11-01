import { Command } from '../../core/Command';

export class RegisterNewUserCommand extends Command<any> {

    constructor() {
        super('http://localhost:4201/gigs/rest/user/register', 'POST');
    }
}
