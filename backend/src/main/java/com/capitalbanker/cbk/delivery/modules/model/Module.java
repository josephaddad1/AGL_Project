package com.capitalbanker.cbk.delivery.modules.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cbk_modules")
public class Module {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "parent_appl")
	private String parentApplName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentApplName() {
		return parentApplName;
	}

	public void setParentApplName(String parentApplName) {
		this.parentApplName = parentApplName;
	}

}
