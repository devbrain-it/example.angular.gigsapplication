import { Command } from '../../core/Command';

export class NewCityCommand extends Command<number> {

    constructor(cityName: string) {
        super(`http://localhost:4201/gigs/rest/city/{cityName}`, 'PUT');
    }
}
