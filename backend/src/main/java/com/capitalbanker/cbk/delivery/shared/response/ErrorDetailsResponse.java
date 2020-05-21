 package com.capitalbanker.cbk.delivery.shared.response;

import java.util.Date;

public class ErrorDetailsResponse extends ServiceResponseBase<String> {
	private Date timestamp;
	private int status;
	private String error;

	public ErrorDetailsResponse(int status, Date timestamp, String message, String error) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
	}

	public ErrorDetailsResponse(String error, Date timestamp) {
		this.error=error;
		this.timestamp = timestamp;
	}
	
	public ErrorDetailsResponse(String error, Date timestamp,String message) {
		this.status= Integer.parseInt(error.substring(0, 3));
		this.error=message;
		this.timestamp = timestamp;
		this.message=message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTimestamp() {
		return timestamp.toString();
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

//	@Override
//	public String toString() {
//		Gson gson = new Gson();
//		return gson.toJson(this);
//	}
}