import { Injectable } from '@angular/core';
import { DialogComponent } from '../core/dialog/dialog.component';
import { DialogButton } from '../core/dialog/model/DialogButton';

let dialogComponent: DialogComponent;

@Injectable()
export class DialogService {

    setDialogComponent(component: DialogComponent) {
        dialogComponent = component;
    }

    showDialog(title: string, html: any, buttons?: DialogButton[], delay: number = 600) {
        if (dialogComponent != null) {
            dialogComponent.showDialog(title, html, buttons, delay);
        } else {
            alert('Component is unset!');
        }
    }
}
