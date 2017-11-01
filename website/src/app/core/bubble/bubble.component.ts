import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import $ from 'jquery';

@Component({
    // tslint:disable-next-line:component-selector
    selector: 'bubble',
    templateUrl: './bubble.component.html',
    styleUrls: ['./../../core/dialog/dialog.component.css']
})
export class BubbleComponent implements OnInit {
    fadeInMillis = 400;
    fadeOutMillis = 400;
    code = '<strong>Demo</strong>';
    title: string;
    @ViewChild('bubbleView') element: ElementRef;
    private readonly nextBubble = new Array<({ 'html': string, 'delay': number })>();

    constructor() {
    }

    ngOnInit(): void {
        $(this.element.nativeElement).click(() => $(this.element.nativeElement).hide());
        $(this.element.nativeElement).hide();
    }

    hide() {
        $(this.element.nativeElement).hide();
    }

    bubble(duration: number, html: string = '', push: boolean = true) {
        if (push === true) {
            this.nextBubble.push({
                html: html,
                delay: duration
            });
        }

        const value = this.nextBubble.pop();
        this.code = value.html;
        $(this.element.nativeElement)
            .fadeIn(this.fadeInMillis)
            .delay(value.delay)
            .fadeOut(this.fadeOutMillis, () => {
                if (this.nextBubble.length > 0) {
                    console.log('Nächste Bubble!');

                    const val = this.nextBubble.pop();
                    this.bubble(val.delay, val.html, false);
                }
            });
    }
}
