package com.capitalbanker.cbk.delivery.modules.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cbk_modules_v")
public class ModuleView {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "application_name")
	private String applicationName;

	@Column(name = "module_adf_lib")
	private String moduleAdfLib;

	@Column(name = "view_adf_lib")
	private String viewAdfLib;

	@Column(name = "sub_module_name")
	private String subModuleName;

	@Column(name = "appl")
	private String appl;

	@Column(name = "java_obj")
	private String javaObj;

	@Column(name = "java_obj_version")
	private String javaObjVersion;

	@Column(name = "sub_module_id")
	private String subModuleId;

	@Column(name = "module_id")
	private String moduleId;

	@Column(name = "module_name")
	private String moduleName;

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getSubModuleId() {
		return subModuleId;
	}

	public void setSubModuleId(String subModuleId) {
		this.subModuleId = subModuleId;
	}

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

	public String getModuleAdfLib() {
		return moduleAdfLib;
	}

	public void setModuleAdfLib(String moduleAdfLib) {
		this.moduleAdfLib = moduleAdfLib;
	}

	public String getViewAdfLib() {
		return viewAdfLib;
	}

	public void setViewAdfLib(String viewAdfLib) {
		this.viewAdfLib = viewAdfLib;
	}

	public String getSubModuleName() {
		return subModuleName;
	}

	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}

	public String getAppl() {
		return appl;
	}

	public void setAppl(String appl) {
		this.appl = appl;
	}

	public String getJavaObj() {
		return javaObj;
	}

	public void setJavaObj(String javaObj) {
		this.javaObj = javaObj;
	}

	public String getJavaObjVersion() {
		return javaObjVersion;
	}

	public void setJavaObjVersion(String javaObjVersion) {
		this.javaObjVersion = javaObjVersion;
	}

}
