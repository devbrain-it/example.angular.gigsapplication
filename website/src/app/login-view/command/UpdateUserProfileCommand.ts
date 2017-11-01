import { Command } from '../../core/Command';

export class UpdateUserProfileCommand extends Command<any> {

    constructor() {
        super('http://localhost:4201/gigs/rest/user/profile/update');
    }

    getMethod(): string {
        return 'POST';
    }
}
