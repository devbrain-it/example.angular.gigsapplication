import $ from 'jquery';
import { HTTPUtils } from './http.utils';

export abstract class Command<T> {
    solved: Function;
    catched: Function;

    constructor(private readonly url: string, private readonly method: string = 'GET') {
    }

    getUrl(): string {
        return this.url;
    }

    getMethod(): string {
        return this.method != null ? this.method : 'GET';
    }

    getRequestDataType() {
        return 'json';
    }

    then(dataCallback: Function): Command<T> {
        this.solved = dataCallback;
        return this;
    }

    catch(dataErrorCallback: Function): Command<T> {
        this.catched = dataErrorCallback;
        return this;
    }

    execute(data?) {
        const url: string = this.getUrl();

        HTTPUtils.send<T>(url, url, this.getMethod(), data, this.getRequestDataType())
            .catch(err => {
                if (this.catch != null) {
                    this.catch(err);
                }
            })
            .then(val => {
                if (this.solved) {
                    this.solved(val);
                }
            });

        // // debuginfo
        // console.log(`$> ${url}`);
        // $.ajax({
        //     url: url,
        //     dataType: this.getRequestDataType(),
        //     method: this.getMethod(),
        //     data: data,
        //     error: error => {
        //         console.log(`ERROR - ${error}`);
        //         if (this.catch != null) {
        //             this.catch(error);
        //         }
        //     },
        //     success: (jsonData: T) => {
        //         console.log('INFO - RESPONSE OK');
        //         if (this.solved) {
        //             this.solved(jsonData);
        //         }
        //     }
        // });
    }
}
