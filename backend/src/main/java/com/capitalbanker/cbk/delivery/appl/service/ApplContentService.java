package com.capitalbanker.cbk.delivery.appl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitalbanker.cbk.delivery.appl.model.ApplContent;
import com.capitalbanker.cbk.delivery.appl.repository.ApplContentRepo;

@Service
public class ApplContentService {

	@Autowired
	ApplContentRepo applContentRepo;
	
	
	
	public List<ApplContent> findByApplId(String applId){
		return applContentRepo.findByApplId(applId);
	}
	
	public List<ApplContent> findByParentId(String parentId){
		return applContentRepo.findByParentId(parentId);
	}
	
}
