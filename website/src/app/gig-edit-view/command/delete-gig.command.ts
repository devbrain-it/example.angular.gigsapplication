import { Command } from '../../core/Command';

export class DeleteGigCommand extends Command<any> {

    constructor(gigId: number) {
        super('http://localhost:4201/gigs/rest/gig/{gigId}', 'DELETE');
    }
}
