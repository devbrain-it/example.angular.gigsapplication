import { Injectable, Inject, ViewChild, OnInit } from '@angular/core';
import { BubbleComponent } from '../core/bubble/bubble.component';

let bubbleComponent: BubbleComponent;

@Injectable()
export class BubbleService implements OnInit {

    constructor() {
    }

    setBubbleComponent(component: BubbleComponent) {
        bubbleComponent = component;
    }

    showBubble(delay: number, html: any) {
        if (bubbleComponent != null) {
            console.log(`bubble: ${html}`);
            bubbleComponent.bubble(delay, html);
        } else {
            alert('Component is unset!');
        }
    }

    ngOnInit(): void {
        alert(bubbleComponent);
    }
}
