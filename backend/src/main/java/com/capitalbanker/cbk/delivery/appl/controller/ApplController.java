package com.capitalbanker.cbk.delivery.appl.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capitalbanker.cbk.delivery.appl.model.Appl;
import com.capitalbanker.cbk.delivery.appl.model.ApplContent;
import com.capitalbanker.cbk.delivery.appl.service.ApplContentService;
import com.capitalbanker.cbk.delivery.appl.service.ApplService;
import com.capitalbanker.cbk.delivery.delivery.model.DeliveryDetails;
import com.capitalbanker.cbk.delivery.shared.response.ErrorDetailsResponse;
import com.capitalbanker.cbk.delivery.shared.response.ServiceResponseBase;
import com.capitalbanker.cgb.buisnessobjects.ApplBO;

@RestController
@RequestMapping(value = "/appls")
public class ApplController {

	@Autowired
	ApplService applService;

	@Autowired
	ApplContentService applContentService;

	@GetMapping(value = "/appl-in")
	public ServiceResponseBase<List<Appl>> getApplsBydelivery(@RequestParam String applName) throws Exception {
		try {

			return new ServiceResponseBase<List<Appl>>(applService.getApplIn(applName), null);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@PostMapping(value = "/appl-by-name")
	public ServiceResponseBase<List<Appl>> getApplsBydeliveryLike(@RequestBody Appl appl) throws Exception {
		try {

			return new ServiceResponseBase<List<Appl>>(applService.applCustomSearch(appl), null);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@GetMapping(value = "/appl-content")
	public ServiceResponseBase<List<ApplContent>> getApplContent(@RequestParam String applId) throws Exception {
		try {

			return new ServiceResponseBase<List<ApplContent>>(applContentService.findByApplId(applId), null);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@GetMapping(value = "/appl-content-by-parent-id")
	public ServiceResponseBase<List<ApplContent>> getApplContentByParentId(@RequestParam String parentId)
			throws Exception {
		try {

			return new ServiceResponseBase<List<ApplContent>>(applContentService.findByParentId(parentId), null);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@GetMapping(value = "/update-appl")
	public ServiceResponseBase<String> updateAppl(@RequestParam String applId, @RequestParam String updatedBy)
			throws Exception {
		try {

			ApplBO appl = applService.updateAppl(applId, updatedBy);

			if (appl.hasAnyError()) {
				return new ErrorDetailsResponse(appl.getFileBOErrors().toString(), new Date());
			}

			String warning = null;

			if (appl.hasAnyWarning()) {
				warning = appl.getFileBOWarnings().toString();
			}

			return new ServiceResponseBase<String>("Successfully Updated", warning);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
