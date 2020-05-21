export class DeliveryView {

  constructor(
    public id: string,
    public functionality: string,
    public typeId: string,
    public type: string,
    public clientId: string,
    public client: string,
    public sourceId: string,
    public source: string,
    public ticket: string,
    public appl: string, 
    public deliveredById: string,
    public deliveredBy: string,

    // format (yyyy-MM-dd)
    public releaseDate: string,
    public toDate:string,
    public apprv: string,
    public delivered: string,
    public comments: string,
    public incApp: string,
    public releaseId:string,
    public createdDate :Date) { }

}
