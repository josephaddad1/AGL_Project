package com.capitalbanker.cbk.delivery.shared.response;

import java.io.Serializable;

import com.google.gson.Gson;

public class ServiceResponseBase<T> implements Serializable {

	protected T payLoad;
	protected String message;
	
	public ServiceResponseBase(T payLoad, String message) {
		
		this.payLoad = payLoad;
		this.message = message;
		
	}

	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public ServiceResponseBase() {
		// TODO Auto-generated constructor stub
	}

	public T getPayLoad() {
		return payLoad;
	}
	public void setPayLoad(T payLoad) {
		this.payLoad = payLoad;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
}
