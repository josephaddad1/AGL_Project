package com.capitalbanker.cbk.delivery.modules.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cbk_sub_modules_apps")
public class SubModulesApp {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "application_name")
	private String applicationName;

	@Column(name = "module_adf_lib")
	private String moduleAdfLib;

	public String getModuleAdfLib() {
		return moduleAdfLib;
	}

	public void setModuleAdfLib(String moduleAdfLib) {
		this.moduleAdfLib = moduleAdfLib;
	}

	@Column(name = "view_adf_lib")
	private String viewAdfLib;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getViewAdfLib() {
		return viewAdfLib;
	}

	public void setViewAdfLib(String viewAdfLib) {
		this.viewAdfLib = viewAdfLib;
	}

	public String getSubModuleId() {
		return subModuleId;
	}

	public void setSubModuleId(String subModuleId) {
		this.subModuleId = subModuleId;
	}

	@Column(name = "sub_module_id")
	private String subModuleId;

}
