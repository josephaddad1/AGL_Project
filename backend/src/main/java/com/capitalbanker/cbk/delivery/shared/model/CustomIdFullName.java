package com.capitalbanker.cbk.delivery.shared.model;

public class CustomIdFullName {

	public CustomIdFullName(String id, String fullName) {
		super();
		Id = id;
		this.fullName = fullName;
	}

	String Id;
	
	String fullName;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
