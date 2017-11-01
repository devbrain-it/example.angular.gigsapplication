import { Command } from '../../core/Command';
import { GigJSON } from '../../login-view/model/GigJSON';
import { IJsonMapper } from '../../core/IJsonMapper';
import { GigViewModel } from '../../gig-table/model/GigViewModel';
import { LoginService } from '../../_services/login.service';
import { DialogService } from '../../_services/dialog.service';
import { MainViewService } from '../../_services/main-view.service';

let $LoginService: LoginService;
let $DialogService: DialogService;

export class GetAllGigsCommand extends Command<GigJSON[]> {

    constructor() {
        super('http://localhost:4201/gigs/rest/gigs', 'GET');
    }
}

export class GigDataJsonMapper implements IJsonMapper<GigJSON, GigViewModel> {

    constructor(private ls: LoginService, private ds: DialogService) {
        $LoginService = ls;
        $DialogService = ds;
    }

    map(json: GigJSON): GigViewModel {
        return new GigViewModel(
            json,
            false,
            $DialogService,
            $LoginService);
    }
}
