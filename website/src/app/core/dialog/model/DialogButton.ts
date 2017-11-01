export class DialogButton {
    title = 'OK';
    tooltip: string;
    action: Function;
    doneEvent: Function;

    constructor(operation: Function) {
        const _this: DialogButton = this;
        this.action = () => {
            if (operation != null) {
                operation();
            }

            const handler = _this.doneEvent;
            if (handler != null) {
                handler();
            }
        };
    }
}
