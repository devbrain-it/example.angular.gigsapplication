import { Component, Injectable, OnInit, ElementRef, ViewChild, ViewContainerRef } from '@angular/core';
import { Input, Type, ComponentRef, ComponentFactoryResolver, Compiler, ContentChild } from '@angular/core';
import $ from 'jquery';
import { DialogButton } from './model/DialogButton';
import { AppComponent } from '../../app.component';

let dialogView: any;

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'dialog-view',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
  //  providers: [ComponentFactoryResolver, Compiler]
})
export class DialogComponent implements OnInit {

  title = '';
  buttons: DialogButton[] = [new DialogButton(() => { })];

  @ViewChild('dialogView')
  view: ElementRef;

  html: any;

  constructor() {
  }

  avoidClick() {

  }

  ngOnInit(): void {
    $(this.view.nativeElement).hide();
    $(this.view.nativeElement).parent().click(this.dismiss);
    dialogView = this.view;
  }

  showDialog(title: string, content: any, buttons?: DialogButton[], delay: number = 600): any {
    this.title = title;
    this.html = content;
    const _view = this.view.nativeElement;
    // dismiss always any button!
    for (const button of buttons) {
      button.doneEvent = () => $(_view).fadeOut(delay);
    }
    this.buttons = buttons;
    $(_view).fadeIn(delay);
  }

  dismiss() {
    $('#dialog_container').fadeOut(600);
  }
}
