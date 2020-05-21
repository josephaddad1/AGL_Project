package com.capitalbanker.cbk.delivery.modules.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capitalbanker.cbk.delivery.modules.model.ModuleView;

public interface ModuleViewRepo extends JpaRepository<ModuleView, String> {

	@Query(value = "select d from ModuleView d where (:applicationName is null or  lower(d.applicationName) like lower(concat('%',:applicationName,'%'))) and"
			+ "(:moduleAdfLib is null or  lower(d.moduleAdfLib) like lower(concat('%',:moduleAdfLib,'%'))) and"
			+ "(:viewAdfLib is null or  lower(d.viewAdfLib) like lower(concat('%',:viewAdfLib,'%')))and"
			+ "(:appl is null or  lower(d.appl) like lower(concat('%',:appl,'%')))and"
			+ "(:javaObj is null or  lower(d.javaObj) like lower(concat('%',:javaObj,'%')))and"
			+ "(:subModuleName is null or  lower(d.subModuleName) like lower(concat('%',:subModuleName,'%')))and"
			+ "(:moduleId is null or  lower(d.moduleId) like lower(concat('%',:moduleId,'%')))")

	Page<ModuleView> customSearch(@Param("applicationName") String applicationName,
			@Param("moduleAdfLib") String moduleAdfLib, @Param("viewAdfLib") String viewAdfLib,
		    @Param("javaObj") String javaObj,
			@Param("moduleId") String moduleId, @Param("subModuleName") String subModuleName, @Param("appl") String appl,
			Pageable pageable);

}
