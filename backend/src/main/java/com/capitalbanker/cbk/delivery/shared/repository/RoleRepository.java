package com.capitalbanker.cbk.delivery.shared.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName;
import com.capitalbanker.cbk.delivery.shared.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	@Query("select NEW com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName(r.id,r.description) from Role r")
	List<CustomIdFullName> getRoles(); 

}
