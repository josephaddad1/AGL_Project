package com.capitalbanker.cbk.delivery.shared.response;

import com.capitalbanker.cbk.delivery.shared.model.TokenObject;
import com.capitalbanker.cbk.delivery.shared.model.UserObject;

public class UserLoginResponse {

	private String id;
	private String username;
	private String fullName;
	private String consultant;
	private String role;
	private TokenObject tokenObject;

	public UserLoginResponse(TokenObject tokenObject, UserObject user) {
		if (user != null) {
//
			this.id = user.getId();
			this.username = user.getUsername();
			this.fullName = user.getFullName();
			this.consultant=user.getConsultant_flag();
			
			if(user.getRole()!=null)
			this.role = user.getRole().getCode();
		}

		this.tokenObject = tokenObject;

	}

	public String getConsultant() {
		return consultant;
	}

	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public TokenObject getTokenObject() {
		return tokenObject;
	}

	public void setTokenObject(TokenObject tokenObject) {
		this.tokenObject = tokenObject;
	}

	
}
