package com.capitalbanker.cbk.delivery.delivery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cbk_delivery_details_objects")
public class DeliveryDetailsObject {
	
	
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "object_name")
	private String objectName;
	
					
	@Column(name = "DELIVEY_DETAILS_ID")
	private String deliveryDetailsId;

	@Column(name = "VERSION")
	private String version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getDeliveryDetailsId() {
		return deliveryDetailsId;
	}

	public void setDeliveryDetailsId(String deliveryDetailsId) {
		this.deliveryDetailsId = deliveryDetailsId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
	

}
