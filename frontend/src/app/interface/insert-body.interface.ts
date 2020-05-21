import { Delivery } from './delivery.interface';
import { DeliveryDetails } from './delivery-details.interface';

export class InsertBody {

    constructor(
        public delivery: Delivery,
        public deliveryDetails: DeliveryDetails) { }
}
