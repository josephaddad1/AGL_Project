export class DeliveryDetails {
    constructor(
        public deliveryDetailsIdList: Array<string>,
        public applList: Array<string>,
        public createdBy: string,
        public createdDate: Date,
        public updatedBy: string,
        public updatedDate: string) { }
}