package com.capitalbanker.cbk.delivery.modules.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capitalbanker.cbk.delivery.modules.model.SubModule;
import com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName;

public interface SubModuleRepo extends JpaRepository<SubModule, String> {

	@Query("select m.id from SubModule m where m.appl=:appl")
	public String findIdByAppl(@Param("appl") String appl);

	@Query("select m from SubModule m where lower(m.javaObj) = lower(:javaObj)")
	public SubModule javaObjectVersion(@Param("javaObj") String javaObjName);

	@Query("select NEW com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName(m.id,m.name) from SubModule m where (:moduleId is not null and lower(m.moduleId) like  lower(concat('%',:moduleId,'%')))"
			+ " and m.name is not null")
	public List<CustomIdFullName> findAllByModuleId(String moduleId);

}
