import { Command } from '../../core/Command';
import { CityViewModel } from '../model/city.model';

export class SaveNewCityCommand extends Command<any> {

    constructor(city: CityViewModel) {
        super(`http://localhost:4201/gigs/rest/city/{city.id}/name/{city.name}`);
    }

    getMethod(): string {
        return 'PUT';
    }
}
