package com.capitalbanker.cbk.delivery.delivery.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "cbk_delivery_v")
public class DeliveryView {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "FUNCTIONALITY")
	private String functionality;

	@Column(name = "type_id")
	private String typeId;

	@Column(name = "type")
	private String type;

	@Column(name = "client_id")
	private String clientId;

	@Column(name = "client")
	private String client;

	@Column(name = "source_id")
	private String sourceId;

	@Column(name = "source")
	private String source;

	@Column(name = "ticket")
	private String ticket;

	@Column(name = "appl")
	private String appl;

	@Column(name = "delivered_by_id")
	private String deliveredById;

	@Column(name = "delivered_by")
	private String deliveredBy;

	@Column(name = "release_id")
	private String releaseId;

	@Column(name = "release_date")
	private Date releaseDate;
	
	@Column(name="release_version")
	private String releaseVersion;

	@Transient
	private Date toDate;

	@Column(name = "apprv_flag")
	private String apprv;

	@Column(name = "delivered_flag")
	private String delivered;

	@Column(name = "comments")
	private String comments;

	@Column(name = "inc_app_flag")
	private String incApp;

	@Column(name = "ERROR_FLAG")
	private String errorFlag;

	public String getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public DeliveryView() {

	}



	public String getReleaseVersion() {
		return releaseVersion;
	}

	public void setReleaseVersion(String releaseVersion) {
		this.releaseVersion = releaseVersion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFunctionality() {
		return functionality;
	}

	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getAppl() {
		return appl;
	}

	public void setAppl(String appl) {
		this.appl = appl;
	}

	public String getDeliveredById() {
		return deliveredById;
	}

	public void setDeliveredById(String deliveredById) {
		this.deliveredById = deliveredById;
	}

	public String getDeliveredBy() {
		return deliveredBy;
	}

	public void setDeliveredBy(String deliveredBy) {
		this.deliveredBy = deliveredBy;
	}

	public Date getReleaseDate() {

		return releaseDate;
	}

	// date format : yyyy-MM-dd
	public void setReleaseDate(String releaseDate) {
		if (releaseDate != null)
			this.releaseDate = java.sql.Date.valueOf(releaseDate);
	}

	public String getApprv() {
		return apprv;
	}

	public void setApprv(String apprv) {
		this.apprv = apprv;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getIncApp() {
		return incApp;
	}

	public void setIncApp(String incApp) {
		this.incApp = incApp;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getDelivered() {
		return delivered;
	}

	public void setDelivered(String delivered) {
		this.delivered = delivered;
	}

	public Date getToDate() throws ParseException {
		if (toDate != null) {
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
			return format2.parse(format2.format(toDate));
		}
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
