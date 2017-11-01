import { Component, OnInit, Input } from '@angular/core';
import { GigViewModel } from '../gig-table/model/GigViewModel';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'gig-list-item',
  templateUrl: './gig-list-item.component.html',
  styleUrls: ['./gig-list-item.component.css']
})
export class GigListItemComponent implements OnInit {

  @Input() model: GigViewModel;
  @Input() index: number;

  constructor() { }

  ngOnInit() {
  }

}
