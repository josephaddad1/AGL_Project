import { ServiceResponseBase } from './service-response-base-interface';
import { TokenObject } from './token-object.interface';

export class UserLoginResponse {

    constructor(
        public id: string,
        public username: string,
        public fullName: string,
        public role: string,
        public tokenObject: TokenObject, public consultant) {

    }

}