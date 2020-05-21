package com.capitalbanker.cbk.delivery.appl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.capitalbanker.cbk.delivery.shared.model.FileBOType;
import com.capitalbanker.cgb.buisnessobjects.FileBO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "appl_content")
public class ApplContent {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "object_name")
	private String objectName;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "object_type_id", insertable = false, updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)

	private FileBOType objectType;

	@Column(name = "object_type_id", unique = true, updatable = true, nullable = true)
	private String objectTypeId;

	public void setObjectTypeId(String objectTypeId) {
		this.objectTypeId = objectTypeId;
	}

	@Column(name = "version")
	private String version;

	@Column(name = "appl_id")
	private String applId;

	@Column(name = "parent_Id")
	private String parentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public FileBOType getObjectType() {
		return objectType;
	}

	public void setObjectType(FileBOType objectType) {
		this.objectType = objectType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getApplId() {
		return applId;
	}

	public void setApplId(String applId) {
		this.applId = applId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
