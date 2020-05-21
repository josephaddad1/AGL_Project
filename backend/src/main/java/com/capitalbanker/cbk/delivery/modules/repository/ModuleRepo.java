package com.capitalbanker.cbk.delivery.modules.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capitalbanker.cbk.delivery.modules.model.Module;
import com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName;;

public interface ModuleRepo extends JpaRepository<Module, String> {

	@Query("select NEW com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName(m.id,m.name) from Module m where (:moduleName is not null and lower(m.name) like  lower(concat('%',:moduleName,'%')))"
			+ " and m.name is not null")
	public List<CustomIdFullName> getModules(@Param("moduleName") String moduleName);
}
