package com.capitalbanker.cbk.delivery.delivery.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.capitalbanker.cbk.delivery.delivery.model.DeliveryDetailsObject;

public interface DeliveryDetailsObjectRepo extends JpaRepository<DeliveryDetailsObject, String> {

	
	DeliveryDetailsObject findByDeliveryDetailsId(String deliveryDetailsId);
	
	
	@Transactional
	@Modifying
	void deleteByDeliveryDetailsId(String deliveryDetailsId);
}
