import { Command } from '../../core/Command';
import { ArtistViewModel } from '../model/artist..model';

export class DeleteArtistCommand extends Command<any> {

    constructor(artist: ArtistViewModel) {
        super(`http://localhost:4201/gigs/rest/artist/{artist.id}`, 'DELETE');
    }
}
