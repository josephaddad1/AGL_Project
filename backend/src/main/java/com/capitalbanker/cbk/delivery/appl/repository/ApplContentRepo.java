package com.capitalbanker.cbk.delivery.appl.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.capitalbanker.cbk.delivery.appl.model.ApplContent;

public interface ApplContentRepo extends JpaRepository<ApplContent, String> {

	List<ApplContent> findByApplId(String applId);
	
	List<ApplContent> findByParentId(String parentId);
	
	
	@Transactional
	@Modifying
	void deleteByApplId(String applId);
}
