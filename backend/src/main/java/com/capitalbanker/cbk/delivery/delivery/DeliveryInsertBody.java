package com.capitalbanker.cbk.delivery.delivery;

import com.capitalbanker.cbk.delivery.delivery.model.Delivery;
import com.capitalbanker.cbk.delivery.delivery.model.DeliveryDetails;

public class DeliveryInsertBody {

	private Delivery delivery;

	private DeliveryDetails deliveryDetails;

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public DeliveryDetails getDeliveryDetails() {
		return deliveryDetails;
	}

	public void setDeliveryDetails(DeliveryDetails deliveryDetails) {
		this.deliveryDetails = deliveryDetails;
	}

}
