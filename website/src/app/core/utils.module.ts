import { NgModule } from '@angular/core';


@NgModule(({
    declarations: [],
    imports: [],
    providers: [],
    bootstrap: []
  }) as any)
export class UtilsModule {

    public static readonly isString: Function = obj => {
        const toString = Object.prototype.toString;
        return toString.call(obj) === '[object String]';
    };

    public static readonly fail = (test: Function) => {
        try {
            test();
            return false;
        } catch (e) {
            return true;
        }
    };

    public static readonly joinStrings = (errors: Array<string>, rowFormat: Function) => {
        const delimiter = '\n';
        let text = '';
        for (const txt of errors) {
            if (text.length > 0) {
                text = text + delimiter;
            }
            text = text + (rowFormat != null ? rowFormat(txt) : txt);
        }
        return text;
    };

    public static readonly isStringNullOrEmpty = (text: string) => {
        return text == null || text.trim().length === 0;
    };

    public static readonly hashCode = (str: String) => {
        let hash = 0;
        if (str.length === 0) {
            return hash;
        }
        for (let i = 0; i < str.length; i++) {
            const char = str.charCodeAt(i);
            // tslint:disable-next-line:no-bitwise
            hash = ((hash << 5) - hash) + char;
            // tslint:disable-next-line:no-bitwise
            hash = hash & hash; // Convert to 32bit integer
        }
        return hash;
    };

    public static readonly isInt = obj => {
        const pattern = new RegExp('^\\d+$');
        return pattern.test(obj);
    };

}
