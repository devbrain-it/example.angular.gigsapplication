import { MinifiedJSON } from '../../gig-edit-view/model/minified.json';
import { Input } from '@angular/core';

export class IdObject implements MinifiedJSON {

    @Input() id: number;
    @Input() name: string;
    editSelected: boolean;
    disabled: boolean;

    constructor(id: number, name: string) {
        this.id = id;
        this.name = name;
    }
}
