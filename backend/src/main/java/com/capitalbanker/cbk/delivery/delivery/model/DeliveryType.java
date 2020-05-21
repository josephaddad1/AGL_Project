package com.capitalbanker.cbk.delivery.delivery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cbk_delivery_types")
public class DeliveryType {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "code")
	private String code;

	@Column(name = "description")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
