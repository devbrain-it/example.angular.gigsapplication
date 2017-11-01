export class CallbackPromise<T> {
    catcher: Function;
    resolve: Function;
    readonly call: Function;

    constructor(call: Function) {
        this.call = call;
    }

    then(dataResolver: Function): CallbackPromise<T> {
        this.resolve = dataResolver;
        return this;
    }

    catch(dataCatch: Function): CallbackPromise<T> {
        this.catcher = dataCatch;
        return this;
    }

    run(): CallbackPromise<T> {
        this.call(this).then(this.resolve).catch(this.catcher);
        return this;
    }
}
