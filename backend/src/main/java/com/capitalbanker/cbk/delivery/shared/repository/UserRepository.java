package com.capitalbanker.cbk.delivery.shared.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName;
import com.capitalbanker.cbk.delivery.shared.model.UserObject;


@Repository
public interface UserRepository extends JpaRepository<UserObject, String> {
	UserObject findByUsername(String username);
	
	
	

	
	@Query("select NEW com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName(u.id,u.fullName) from UserObject u where (:fullName is not null and lower(fullName) like  lower(concat('%',:fullName,'%')))"
			+ " and fullName is not null")
	public List<CustomIdFullName> getUsersList(@Param("fullName") String fullName);
	
	@Query("select u from UserObject u where (:fullName is not null and lower(u.fullName) like  lower(concat('%',:fullName,'%')))"
			+ " and fullName is not null")
	public Page<UserObject> getUser(@Param("fullName") String fullName,Pageable pageable);
	
	
	@Query("select NEW com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName(u.id,u.fullName) from UserObject u where (:fullName is not null and lower(fullName) like  lower(concat('%',:fullName,'%'))) and consultant_flag='N'"
			+ " and fullName is not null")
	public List<CustomIdFullName> getDev(@Param("fullName") String fullName);
	
	
@Query("select NEW com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName(u.id,u.fullName) from UserObject u where (:fullName is not null and lower(fullName) like  lower(concat('%',:fullName,'%'))) and consultant_flag='O'"
		+ " and fullName is not null")
public List<CustomIdFullName> getConsultant(@Param("fullName") String fullName);
}
