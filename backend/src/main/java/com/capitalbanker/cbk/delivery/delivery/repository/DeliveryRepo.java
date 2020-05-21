package com.capitalbanker.cbk.delivery.delivery.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capitalbanker.cbk.delivery.delivery.model.Delivery;

public interface DeliveryRepo extends JpaRepository<Delivery, String> {

	public Collection<Delivery> findByClientId(String id);

	public Collection<Delivery> findByNotesContaining(String note);

//	@Query("Select d ,cbk from cbk_deliveries where (:notes is null or d.notes like '%:notes%) and (:type is null or d. = :notes)")
//	public Collection<Deliveries> findBy(@Param("notes") String notes, @Param());
//	
}
