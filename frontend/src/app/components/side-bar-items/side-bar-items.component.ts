import { Component, OnInit, Input } from '@angular/core';

import { trigger, state, transition, animate, style } from '@angular/animations';
import { Router } from '@angular/router';

@Component({
  selector: 'app-side-bar-items',
  templateUrl: './side-bar-items.component.html',
  styleUrls: ['./side-bar-items.component.css'], animations: [
    trigger('indicatorRotate', [
      state('collapsed', style({ transform: 'rotate(0deg)' })),
      state('expanded', style({ transform: 'rotate(90deg)' })),
      transition('expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4,0.0,0.2,1)')
      ),
    ])
  ],
})
export class SideBarItemsComponent implements OnInit {
  expanded: boolean;

  @Input() parentName: string;
  @Input() childList: Array<any>;


  @Input() child: any;
  @Input() depth: number;


  constructor(private router: Router) { }

  ngOnInit() {
    if (this.child) {
      this.parentName = this.child.name;
    }
  }

  onItemSelected(child) {

    if (!this.depth) {
      this.expanded = !this.expanded;
    } else {
      this.router.navigate(['/' + child.url]);
    }

  }

}
