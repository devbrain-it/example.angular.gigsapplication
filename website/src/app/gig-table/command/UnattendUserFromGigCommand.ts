import { Command } from '../../core/Command';

export class UnattendUserFromGigCommand extends Command<any> {

    constructor(gigId: number, userId: number) {
        super(`http://localhost:4201/gigs/rest/user/${userId}/gig/${gigId}/delete`);
    }
}
