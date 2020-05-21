import { Component, OnInit } from '@angular/core';
import { StateService } from 'src/app/service/state-service/state.service';
import { User } from 'src/app/interface/user.interface';

@Component({
  selector: 'app-users-edit-page',
  templateUrl: './users-edit-page.component.html',
  styleUrls: ['./users-edit-page.component.css']
})
export class UsersEditPageComponent implements OnInit {
  user: User;
  constructor(private state: StateService) { }

  ngOnInit() {
    this.user = this.state.data;
  }

}
