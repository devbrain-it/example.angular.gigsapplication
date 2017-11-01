import { Component, Input, OnInit } from '@angular/core';
import { GigViewModel } from './model/GigViewModel';

@Component({
    // tslint:disable-next-line:component-selector
    selector: 'gig-table',
    templateUrl: './gig-table.component.html',
    styleUrls: ['./gig-table.component.css']
})
export class GigTableComponent implements OnInit {

    // gigs
    @Input() gigs: GigViewModel[];

    constructor() { }

    ngOnInit() {

    }
}
