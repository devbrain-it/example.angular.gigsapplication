import { Command } from '../../core/Command';
import { MinifiedJSON } from '../model/minified.json';

export class GetAllCitiesCommand extends Command<MinifiedJSON[]> {

    constructor() {
        super('http://localhost:4201/gigs/rest/cities', 'GET');
    }
}
