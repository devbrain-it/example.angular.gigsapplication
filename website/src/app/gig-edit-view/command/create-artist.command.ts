import { Command } from '../../core/Command';

export class CreateArtistCommand extends Command<number> {

    constructor(artistName: string) {
        super(`http://localhost:4201/gigs/gigs/artist/{artistName}`, 'PUT');
    }
}
