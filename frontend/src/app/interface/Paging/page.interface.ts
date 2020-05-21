import { Sort } from './sort.interface';
import { Pageable } from './pageable.interface';

export class Page<T> {

  public constructor(public content: Array<T>,
    public pageable: Pageable,
    public last: boolean,
    public totalPages: number,
    public totalElements: number,
    public first: boolean,
    public sort: Sort,
    public numberOfElements: number,
    public size: number,
    public number: number,
    public empty: boolean) {

  }
}
