package com.capitalbanker.cbk.delivery.shared.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.capitalbanker.cbk.delivery.appl.model.ApplContent;

@Entity
@Table(name = "cbk_obj_types")
public class FileBOType {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "code")
	private String code;

	@OneToMany(mappedBy = "objectType")
	private List<ApplContent> applContent;

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
