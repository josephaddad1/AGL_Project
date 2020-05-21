import { Component, OnInit } from '@angular/core';
import { NewTokenService } from 'src/app/service/new-token/new-token.service';
import { HttpRequestService } from 'src/app/service/http-request/http-request.service';
import { TokenObject } from 'src/app/interface/token-object.interface';
import { DeliveryRequestService } from 'src/app/service/delivery-request/delivery-request.service';
import { RolesService } from 'src/app/service/roles-service/roles.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor() {

  }

  ngOnInit() {

  }
}
