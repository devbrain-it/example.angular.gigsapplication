import $ from 'jquery';

export class HTTPUtils {

    public static send<T>(key: string, url: string, method: string, data?: any, dataType: string = 'json'): Promise<T> {
        return new Promise<T>((resolve, reject) => {
            console.log('INFO - RQ: ' + new Date().toUTCString() + ' key: ' + key);
            $.ajax({
                url: url,
                method: method,
                dataType: dataType,
                data: data,
                error: err => {
                    console.log('INFO - RSP: ' + new Date().toUTCString() + ' key: ' + key);
                    console.log('RQ - RSP/ERR: ' + err);
                    reject(err);
                },
                success: val => {
                    console.log('INFO - RSP OK: ' + new Date().toUTCString() + ' key: ' + key);
                    resolve(val);
                }
            });
        });
    }
}
