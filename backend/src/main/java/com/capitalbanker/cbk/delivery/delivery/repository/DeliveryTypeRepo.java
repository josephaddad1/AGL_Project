package com.capitalbanker.cbk.delivery.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capitalbanker.cbk.delivery.shared.model.Client;
import com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName;

public interface DeliveryTypeRepo extends JpaRepository<Client, String> {

	@Query("select NEW com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName(t.id,t.description) from DeliveryType t")
	public List<CustomIdFullName> getTypes();
}
