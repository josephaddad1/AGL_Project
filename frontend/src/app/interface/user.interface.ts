export class User {

  constructor(
    public id: string,
    public fullName: string,
    public username: string,
    public role: any,
    public token: string,
    public refreshToken: string,
    public consultant_flag: string,
    public status_code: string
  ) { }
}
