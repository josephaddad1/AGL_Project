package com.capitalbanker.cbk.delivery.release.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReleaseResponseObject {

	private String version;
	private Collection<Module> modules;
	
	
	public ReleaseResponseObject() {
		modules = new ArrayList<Module>();
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Collection<Module> getModules() {
		return modules;
	}
	public void setModules(Collection<Module> collection) {
		this.modules = collection;
	}
	
	
	
	
}


class Module{
	
	private String name;
	private List<DeliveryInfo> notes;
	private List<JavaObject> javaObjects;
	
	
	
	
	
	public Module() {
		
		this.notes = new ArrayList<DeliveryInfo>();
		this.javaObjects = new ArrayList<JavaObject>();;
	}
	public List<DeliveryInfo> getNotes() {
		return notes;
	}
	public void setNotes(List<DeliveryInfo> notes) {
		this.notes = notes;
	}
	public List<JavaObject> getJavaObjects() {
		return javaObjects;
	}
	public void setJavaObjects(List<JavaObject> javaObjects) {
		this.javaObjects = javaObjects;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}


class DeliveryInfo{
	private String functionality;
	private String jira;
	private	String client;
	private	String type;
	
	public String getFunctionality() {
		return functionality;
	}
	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getJira() {
		return jira;
	}
	public void setJira(String jira) {
		this.jira = jira;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}

class JavaObject{
	private String name;
	private	String version;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
}