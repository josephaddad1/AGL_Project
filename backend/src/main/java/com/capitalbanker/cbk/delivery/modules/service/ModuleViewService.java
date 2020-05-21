package com.capitalbanker.cbk.delivery.modules.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.capitalbanker.cbk.delivery.modules.model.ModuleView;
import com.capitalbanker.cbk.delivery.modules.repository.ModuleViewRepo;

@Service
public class ModuleViewService {
	@Autowired
	private ModuleViewRepo moduleViewRepo;

	public Page<ModuleView> customSearch(ModuleView moduleView, int page, int pageSize) throws Exception {
		try {

			// if the object null to prevent getting all the data from the database due to
			// the select query with is null condition
			if (moduleView.getId() == null && moduleView.getApplicationName() == null
					&& moduleView.getModuleAdfLib() == null && moduleView.getViewAdfLib() == null
					&& moduleView.getJavaObj() == null
					&& moduleView.getJavaObjVersion() == null && moduleView.getModuleId() == null
					&& moduleView.getSubModuleId() == null && moduleView.getAppl() == null) {
				return null;
			}

			return moduleViewRepo.customSearch(moduleView.getApplicationName(), moduleView.getModuleAdfLib(),
					moduleView.getViewAdfLib(), moduleView.getJavaObj(),
					moduleView.getModuleId(), moduleView.getSubModuleName(), moduleView.getAppl(),
					PageRequest.of(page, pageSize));

		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
