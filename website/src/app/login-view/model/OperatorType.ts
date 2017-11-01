export class OperatorType {

    id: number;
    value: string;
    select: boolean;
    description: string;

    constructor(id: number, value: string, selected: boolean = false) {
        this.id = id;
        this.value = value;
        this.select = selected;
    }
}
