export class ServiceResponseBase<T> {
  constructor(public payLoad: T, public message: string) {
  }
}
