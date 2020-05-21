package com.capitalbanker.cbk.delivery.shared.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cbk_clients")
public class Client {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String fullName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

//	public Client(String id, String fullName) {
//		super();
//		this.id = id;
//		this.fullName = fullName;
//	}

}
