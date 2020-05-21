package com.capitalbanker.cbk.delivery.shared.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capitalbanker.cbk.delivery.shared.model.Client;
import com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName;

public interface ClientRepo extends JpaRepository<Client, String> {

	@Query("select NEW com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName(u.id,u.fullName) from Client u where (:clientName is not null and lower(fullName) like  lower(concat('%',:clientName,'%')))"
			+ " and fullName is not null")
	public List<CustomIdFullName> getClient(@Param("clientName") String clientName);
}
