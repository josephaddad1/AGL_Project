package com.capitalbanker.cbk.delivery.delivery.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capitalbanker.cbk.delivery.delivery.model.DeliveryDetails;

public interface DeliveryDetailsRepo extends JpaRepository<DeliveryDetails, String> {

	@Transactional
	@Modifying
	void deleteByDeliveryId(String deliveryId);


	@Query("select d.id from DeliveryDetails d where  d.deliveryId=:deliveryId")
	List<String> findIdByDeliveryId(@Param("deliveryId") String deliveryId);
	
	
	List<DeliveryDetails> findByDeliveryId( String deliveryId);




}
