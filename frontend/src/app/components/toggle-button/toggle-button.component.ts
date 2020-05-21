import { Component, Output, EventEmitter, OnInit, Input } from '@angular/core';

@Component({
  selector: 'toggle-button',
  template: `
  <div class="toggle-button" >
    <input type="checkbox" [id]="text"
       [checked]="checked"  (click)="change()">
    <label class="toggle-button-switch"
      [for]="text"></label>
    <div class="toggle-button-text" (click)="change()">
      <div class="toggle-button-text-on">Yes</div>
      <div class="toggle-button-text-off">No</div>
    </div>
</div>
  `,
  styles: [`
    :host {
      display: block;
      position: relative;
      width: 100px;
    }
    
    input[type="checkbox"] {
      display: none; 
    }
    .toggle-button{
      width:50%;
      height:50%;
    }

    /* 토글 버튼 내부의 원. 감춘 체크박스와 연동한다. */
    .toggle-button-switch {
      position: absolute;
      top: 0px;
      left: 0px;
      width: 26px;
      height: 26px;
      background-color: #fff;
      border-radius: 100%;
      cursor: pointer;
      z-index: 100;
      transition: left 0.3s;
    }

    /* 토글 버튼의 바탕 */
    .toggle-button-text {
      cursor: pointer;
      overflow: hidden;
      background-color: #c53333;
      border-radius: 25px;
      box-shadow: 2px 2px 5px 0 rgba(50, 50, 50, 0.75);
      transition: background-color 0.3s;
      padding-top: 4px;
    height: 26px;
    }

    /* 토글 버튼의 텍스트 */
    .toggle-button-text-on,
    .toggle-button-text-off {
      float: left;
      width: 50%;
      height: 100%;
      font-family: Lato, sans-serif;
      font-weight: bold;
      color: #fff;
      text-align: center;
    }

    /* 체크박스가 체크 상태이면 토글 버튼 내부의 원을 오른쪽으로 52px 이동 */
    input[type="checkbox"]:checked ~ .toggle-button-switch {
      left: 26px;
    }

    /* 체크박스가 체크 상태이면 토글 버튼의 배경색 변경 */
    input[type="checkbox"]:checked ~ .toggle-button-text {
      background-color: #3dbf87;
    }
  `]
})
export class ToggleButtonComponent implements OnInit {
  @Output() changed = new EventEmitter<boolean>();

  @Input() text;
  @Input() checked;
  ngOnInit() {

  }

  emit(event) {
    this.checked = event;
    this.changed.emit(event);

  }

  change() {
    this.checked = !this.checked;
    this.changed.emit(this.checked);
  }
}