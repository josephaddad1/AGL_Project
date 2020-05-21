package com.capitalbanker.cbk.delivery.delivery.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capitalbanker.cbk.delivery.delivery.model.DeliveryView;

public interface DeliveryViewRepo extends JpaRepository<DeliveryView, Integer> {

	@Query("select  distinct releaseDate from DeliveryView where  releaseDate is not null")
	public List<String> getReleaseDates();

	@Query(value = "select d from DeliveryView d where " + " (:type is null or  lower(d.type) = lower(:type))  and"
			+ " (:client is null or lower(d.client) = lower(:client)) and"
			+ " (:source is null or lower(d.source) = lower(:source)) and"
			+ " (:ticket is null or  lower(d.ticket) = lower(:ticket)) and"
			+ " (:appl is null or  lower(d.appl) like lower(concat('%',:appl,'%'))) and"
			+ " (:deliveredBy is null or lower(d.deliveredBy) = lower(:deliveredBy)) and"
			+ " (:fromDate is null or :toDate is null or  (d.releaseDate between :fromDate and :toDate)) and"
			+ " (:apprv is null or  lower(d.apprv) = lower(:apprv)) and"
			+ " (:comments is null or lower(d.comments) = lower(:comments)) and"
			+ " (:incApp is null or  lower(d.incApp) = lower(:incApp)) and" + " (:id is null or  d.id =:id)")
	public Page<DeliveryView> customSearchFuncNull(@Param("id") String id, @Param("type") String type,
			@Param("client") String client, @Param("source") String source, @Param("ticket") String ticket,
			@Param("appl") String appl, @Param("deliveredBy") String deliveredBy, @Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate, @Param("apprv") String apprv, @Param("comments") String comments,
			@Param("incApp") String incApp, Pageable pageable);

	@Query(value = "select d from DeliveryView d where "
			+ "( (lower(d.functionality) like lower(concat('%',:functionality,'%'))) ) and "
			+ " (:type is null or  lower(d.type) = lower(:type))  and"
			+ " (:client is null or lower(d.client) = lower(:client)) and"
			+ " (:source is null or lower(d.source) = lower(:source)) and"
			+ " (:ticket is null or  lower(d.ticket) = lower(:ticket)) and"
			+ " (:appl is null or  lower(d.appl) like lower(concat('%',:appl,'%'))) and"
			+ " (:deliveredBy is null or lower(d.deliveredBy) = lower(:deliveredBy)) and"
			+ " (:fromDate is null or :toDate is null or  (d.releaseDate between :fromDate and :toDate)) and"
			+ " (:apprv is null or  lower(d.apprv) = lower(:apprv)) and"
			+ " (:comments is null or lower(d.comments) = lower(:comments)) and"
			+ " (:incApp is null or  lower(d.incApp) = lower(:incApp) ) and" + " (:id is null or  d.id =:id)")
	public Page<DeliveryView> customSearchFuncNotNull(@Param("id") String id,
			@Param("functionality") String functionality, @Param("type") String type, @Param("client") String client,
			@Param("source") String source, @Param("ticket") String ticket, @Param("appl") String appl,
			@Param("deliveredBy") String deliveredBy, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,
			@Param("apprv") String apprv, @Param("comments") String comments, @Param("incApp") String incApp,
			Pageable pageable);

	
	public List<DeliveryView> findByReleaseVersion(String releaseVersion);
	
	public List<DeliveryView> findByReleaseId (String releaseId);

}
