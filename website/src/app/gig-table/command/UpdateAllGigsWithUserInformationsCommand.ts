import { Command } from '../../core/Command';
import { GigViewModel } from '../model/GigViewModel';
import { UserGigJSON } from '../../login-view/model/gig-user-info.model';

export class UpdateAllGigsWithUserInformationsCommand extends Command<UserGigJSON[]> {

    constructor(userId: number, private gigsData: GigViewModel[]) {
        super(`http://localhost:4201/gigs/rest/user/${userId}/gigs`);
    }
}
