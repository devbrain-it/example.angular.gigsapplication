export interface IJsonMapper<TSource, TResult> {

    map(value: TSource): TResult;
}
