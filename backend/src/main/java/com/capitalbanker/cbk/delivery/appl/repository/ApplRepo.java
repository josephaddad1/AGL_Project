package com.capitalbanker.cbk.delivery.appl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.capitalbanker.cbk.delivery.appl.model.Appl;

@Repository
public interface ApplRepo extends JpaRepository<Appl, String> {

	@Query("SELECT a FROM Appl a WHERE a.name IN :name")
	List<Appl> findByName(List<String> name);
	
	
	@Query("SELECT a FROM Appl a WHERE a.name like lower(concat('%',:name,'%'))")
	List<Appl> findByNameContaining(String name);
	
	
	@Query("SELECT a FROM Appl a WHERE a.name =:name")
	Appl findByName(String name);


	
	@Query("Select a from Appl a , SubModule s where"
			
			+ "(:applName is null or  lower(a.name) LIKE  %:applName%) and"
			+ "(:moduleId is null or s.moduleId  = :moduleId) and"
			+ "(:subModuleId is null or s.id =:subModuleId) and"
			+"(:errorFlag is null or a.errorFlag =:errorFlag) and"
			+"(:fromDate is null or :toDate is null or  (a.applDate between :fromDate and :toDate))"
			+ "and (a.subModuleId=s.id)"
			
			)
	
	List<Appl> applCustomSearch(
			@RequestParam("applName") String applName,
			@RequestParam("moduleId") String moduleId,@RequestParam("subModuleId") String subModuleId,
			@RequestParam("fromDate") Date fromDate,@RequestParam("toDate") Date toDate,@RequestParam("errorFlag") String errorFlag);
	
	 
	
	
}
