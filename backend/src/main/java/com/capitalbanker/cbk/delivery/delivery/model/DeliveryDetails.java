package com.capitalbanker.cbk.delivery.delivery.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cbk_delivery_details")
public class DeliveryDetails {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "appl_Id")
	private String applId;

	@Column(name = "created_by")
	private String createdBy;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "updated_by")
	private String updatedBy;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "delivery_id")
	private String deliveryId;

	@Transient
	private List<String> applList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplId() {
		return applId;
	}

	public void setApplId(String applId) {
		this.applId = applId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		if(createdDate!=null)
		return createdDate.toString();
		return null;
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

	public String getUpdatedDate() {
		if (updatedDate != null) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(updatedDate);
		}
		return null;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	@Transient
	public List<String> getApplList() {
		return applList;
	}

	public void setApplList(List<String> applList) {
		this.applList = applList;
	}

}
