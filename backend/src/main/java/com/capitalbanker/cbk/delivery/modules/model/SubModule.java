package com.capitalbanker.cbk.delivery.modules.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.capitalbanker.cbk.delivery.appl.model.Appl;

@Entity
@Table(name = "cbk_sub_modules")
public class SubModule {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "module_id")
	private String moduleId;

	@Column(name = "appl")
	private String appl;

	@Column(name = "java_obj")
	private String javaObj;

	@Column(name = "java_obj_version")
	private String javaObjVersion;
	
	
	@OneToMany(mappedBy = "subModule")
    private List<Appl> _appl;

	public String getJavaObjVersion() {
		return javaObjVersion;
	}

	public void setJavaObjVersion(String javaObjVersion) {
		this.javaObjVersion = javaObjVersion;
	}

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

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
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

}
