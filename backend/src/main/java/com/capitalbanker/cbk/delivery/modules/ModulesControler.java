package com.capitalbanker.cbk.delivery.modules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capitalbanker.cbk.delivery.modules.model.Module;
import com.capitalbanker.cbk.delivery.modules.model.ModuleView;
import com.capitalbanker.cbk.delivery.modules.model.SubModule;
import com.capitalbanker.cbk.delivery.modules.model.SubModulesApp;
import com.capitalbanker.cbk.delivery.modules.repository.ModuleRepo;
import com.capitalbanker.cbk.delivery.modules.repository.SubModuleAppRepo;
import com.capitalbanker.cbk.delivery.modules.repository.SubModuleRepo;
import com.capitalbanker.cbk.delivery.modules.service.ModuleViewService;
import com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName;
import com.capitalbanker.cbk.delivery.shared.response.ServiceResponseBase;
import com.capitalbanker.cgb.utils.Utils;

@RestController
@RequestMapping(value = "/modules")
public class ModulesControler {

	@Autowired
	ModuleRepo moduleRepo;

	@Autowired
	ModuleViewService moduleViewService;

	@Autowired
	SubModuleRepo subModuleRepo;

	@Autowired
	SubModuleAppRepo subModuleAppsRepo;

	@RequestMapping(value = "/modules-view-list", method = RequestMethod.POST)
	public ServiceResponseBase<Page<ModuleView>> getModulesViewList(@RequestBody ModuleView moduleView,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int pageSize)
			throws Exception {
		return new ServiceResponseBase<Page<ModuleView>>(moduleViewService.customSearch(moduleView, page, pageSize),
				null);
	}

	@RequestMapping(value = "/modules-list", method = RequestMethod.GET)
	public ServiceResponseBase<List<CustomIdFullName>> getModulesList(@RequestParam String moduleName) 
			throws Exception {
		return new ServiceResponseBase<List<CustomIdFullName>>(moduleRepo.getModules(moduleName), null);
	}

	@RequestMapping(value = "/sub-modules-list", method = RequestMethod.GET)
	public ServiceResponseBase<List<CustomIdFullName>> getSubModulesList(@RequestParam String moduleId)
			throws Exception {
		return new ServiceResponseBase<List<CustomIdFullName>>(subModuleRepo.findAllByModuleId(moduleId), null);
	}

	@RequestMapping(value = "/add-module", method = RequestMethod.POST)
	public ServiceResponseBase<String> addModule(@RequestBody Module module) throws Exception {

		module.setId(Utils.getRandomUUID());

		moduleRepo.save(module);

		return new ServiceResponseBase<String>("Module added", null);
	}

	@RequestMapping(value = "/add-subModule", method = RequestMethod.POST)
	public ServiceResponseBase<String> addSubModule(@RequestBody SubModule subModule) throws Exception {

		subModule.setId(Utils.getRandomUUID());

		subModuleRepo.save(subModule);

		return new ServiceResponseBase<String>("SubModule added", null);
	}

	@RequestMapping(value = "/subModuleApps-list", method = RequestMethod.GET)
	public ServiceResponseBase<List<SubModulesApp>> getSubModulesApps() throws Exception {
		return new ServiceResponseBase<List<SubModulesApp>>(subModuleAppsRepo.findAll(), null);
	}

	@RequestMapping(value = "/add-subModuleApp", method = RequestMethod.POST)
	public ServiceResponseBase<String> addSubModulesApp(@RequestBody SubModulesApp subModulesApp) throws Exception {
		subModulesApp.setId(Utils.getRandomUUID());
		subModuleAppsRepo.save(subModulesApp);
		return new ServiceResponseBase<String>("Sub Module application added", null);
	}

	@DeleteMapping(value = "/delete-application")
	public ServiceResponseBase<String> deleteApplication(@RequestParam String applicationId) {
		subModuleAppsRepo.deleteById(applicationId);
		return new ServiceResponseBase<String>("Application successfully deleted", null);
	}

}
