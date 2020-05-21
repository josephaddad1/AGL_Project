package com.capitalbanker.cbk.delivery.shared.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capitalbanker.cbk.delivery.shared.model.FileBOType;

public interface FileBOTypeRepo extends JpaRepository<FileBOType, String> {

	@Query("select t.id from FileBOType t where t.code=:code ")
	public String findIdByCode(@Param("code") String code);

}
