export class ValidationResult {

    errors: Array<string>;

    constructor(errors: string[]) {
        this.errors = errors;
    }

    public append(nextResult: ValidationResult) {
        this.errors = this.errors.concat(nextResult.errors);
    }
}
