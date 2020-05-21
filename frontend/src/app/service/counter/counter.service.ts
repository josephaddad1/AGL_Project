import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CounterService {
  private counter: number;
  constructor() {
    this.counter = 0;
  }


  inc() {
    this.counter++;
  }
  dec() {
    this.counter--;
  }
  get() {
    return this.counter;
  }
}
