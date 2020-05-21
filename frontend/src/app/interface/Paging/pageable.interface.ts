import { Sort } from './sort.interface';

export class Pageable {



  public constructor(public sort?: Sort,
    public pageSize?: number,
    public pageNumber?: number,
    public offset?: number,
    public unpaged?: boolean,
    public paged?: boolean) {

  }
}
