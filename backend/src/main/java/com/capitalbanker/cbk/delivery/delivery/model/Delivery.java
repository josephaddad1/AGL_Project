package com.capitalbanker.cbk.delivery.delivery.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cbk_deliveries")
public class Delivery {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "notes")
	private String notes;

//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "requested_by_id", referencedColumnName = "id")
//	private UserObject requestedBy;
//	
	@Column(name = "requested_by_id")
	private String requestedById;

//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "client_id", referencedColumnName = "id")
//	private Client client;

	@Column(name = "client_id")
	private String clientId;

//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "delivered_by_id", referencedColumnName = "id")
//	private UserObject deliveredBy;

	@Column(name = "DELIVERED_BY_ID")
	private String deliveredById;

	@Column(name = "add_comment")
	private String addComment;

	@Column(name = "jira")
	private String jira;

	@Column(name = "delivered_flag")
	private String deliveredFlag;

	@Column(name = "approved_flag")
	private String approvedFlag;

	@Column(name = "app_needed_flag")
	private String appNeededFlag;

//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "release_id", referencedColumnName = "id")
//	private Release release;

	@Column(name = "release_id")
	private String releaseId;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "updated_date")
	private Date updatedDate;

//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "type_id", referencedColumnName = "id")
//	private DeliveryType type;

	@Column(name = "type_id")
	private String typeById;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

//	public UserObject getRequestedBy() {
//		return requestedBy;
//	}
//	public void setRequestedBy(UserObject requestedBy) {
//		this.requestedBy = requestedBy;
//	}
//
//	public UserObject getDeliveredBy() {
//		return deliveredBy;
//	}
//
//	public void setDeliveredBy(UserObject deliveredBy) {
//		this.deliveredBy = deliveredBy;
//	}
//
//	
//
//	public Client getClient() {
//		return client;
//	}
//
//	public void setClient(Client client) {
//		this.client = client;
//	}

	public String getAddComment() {
		return addComment;
	}

	public void setAddComment(String addComment) {
		this.addComment = addComment;
	}

	public String getJira() {
		return jira;
	}

	public void setJira(String jira) {
		this.jira = jira;
	}

	public String getDeliveredFlag() {
		return deliveredFlag;
	}

	public void setDeliveredFlag(String deliveredFlag) {
		this.deliveredFlag = deliveredFlag;
	}

	public String getApprovedFlag() {
		return approvedFlag;
	}

	public void setApprovedFlag(String approvedFlag) {
		this.approvedFlag = approvedFlag;
	}

	public String getAppNeededFlag() {
		return appNeededFlag;
	}

	public void setAppNeededFlag(String appNeededFlag) {
		this.appNeededFlag = appNeededFlag;
	}

//	public Release getReleaseId() {
//		return release;
//	}
//
//	public void setReleaseId(Release release) {
//		this.release = release;
//	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate.toString();
	}

	public void setCreatedDate(Date createdDate) {

		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getRequestedById() {
		return requestedById;
	}

	public void setRequestedById(String requestedById) {
		this.requestedById = requestedById;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getDeliveredById() {
		return deliveredById;
	}

	public void setDeliveredById(String deliveredById) {
		this.deliveredById = deliveredById;
	}

	public String getTypeById() {
		return typeById;
	}

	public void setTypeById(String typeById) {
		this.typeById = typeById;
	}

	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

//	public DeliveryType getType_id() {
//		return type;
//	}
//
//	public void setType_id(DeliveryType type) {
//		this.type = type;
//	}

}
