package com.capitalbanker.cbk.delivery.shared.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="user_roles")
@Entity
public class UsersRoles {

	public UsersRoles(String userId, String roleId) {
		this.userId=userId;
		this.roleId=roleId;
	}
	


		public UsersRoles() {
			
		}

	@Id
	@Column(name="user_id")
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name="role_id")
	private String roleId;
}
