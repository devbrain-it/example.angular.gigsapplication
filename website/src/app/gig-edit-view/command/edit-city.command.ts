import { Command } from '../../core/Command';
import { CityViewModel } from '../model/city.model';

export class EditCityCommand extends Command<any> {

    constructor(city: CityViewModel) {
        super(`http://localhost:4201/gigs/rest/city/{city.id}/name/{city.name}`, `PUT`);
    }
}
