package com.capitalbanker.cbk.delivery.shared.model;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Joseph.alHaddad
 *
 */
@Entity
@Table(name = "users")
public class UserObject {

	@Id
	@Column(name="id")
	private String id;
	
	@Column(name = " full_name")
	private String fullName;
	
	@Column(name="password")
	private String password;
	
	@Column (name ="status_code")
	private String status_code;
	
	@Column (name ="username")
	private String username;
	
	@Column (name = "consultant_flag")
	private String consultant_flag;
	

	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns
            = @JoinColumn(name = "user_id",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))
    private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getConsultant_flag() {
		return consultant_flag;
	}

	public void setConsultant_flag(String consultant_flag) {
		this.consultant_flag = consultant_flag;
	}
	
	
}