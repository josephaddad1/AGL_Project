package com.capitalbanker.cbk.delivery.release.model;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capitalbanker.cbk.delivery.delivery.model.DeliveryView;
import com.capitalbanker.cbk.delivery.shared.response.ServiceResponseBase;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

@RestController
@RequestMapping(value = "/releases")
public class ReleaseController {

	@Autowired
	private ReleaseService releaseService;

	@GetMapping(value="/new-release")
	public ServiceResponseBase<ReleaseResponseObject> createNewRelease(@RequestParam(value="version") String version,
			@RequestParam(value="userId") String userId)  throws Exception{
		
		try {
			
			
			 
			
			
		return new ServiceResponseBase<ReleaseResponseObject>(

				releaseService.createNewRelease(version),
				
				"This is only test service that returns deliveries with no release.");
	}
	catch( Exception e) {
		throw new Exception (e);
	}}

}
