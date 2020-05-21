package com.capitalbanker.cbk.delivery.release.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitalbanker.cbk.delivery.appl.model.Appl;
import com.capitalbanker.cbk.delivery.appl.repository.ApplRepo;
import com.capitalbanker.cbk.delivery.delivery.model.DeliveryDetails;
import com.capitalbanker.cbk.delivery.delivery.model.DeliveryView;
import com.capitalbanker.cbk.delivery.delivery.repository.DeliveryDetailsRepo;
import com.capitalbanker.cbk.delivery.delivery.repository.DeliveryViewRepo;
import com.capitalbanker.cbk.delivery.modules.model.SubModule;
import com.capitalbanker.cbk.delivery.modules.repository.SubModuleRepo;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

@Service
public class ReleaseService {

	@Autowired
	DeliveryViewRepo deliveryViewRepo;

	@Autowired
	DeliveryDetailsRepo deliveryDetialsRepo;

	@Autowired
	ApplRepo applRepo;

	@Autowired
	SubModuleRepo submoduleRepo;

	public ReleaseResponseObject createNewRelease(String version) {

		List<DeliveryView> noneReleasedDeliveries = null;
				
		noneReleasedDeliveries=deliveryViewRepo.findByReleaseVersion(version);
		
		if(noneReleasedDeliveries==null || noneReleasedDeliveries.isEmpty()) {
			noneReleasedDeliveries=deliveryViewRepo.findByReleaseId(null);
		}

		ReleaseResponseObject releaseResponse = new ReleaseResponseObject();
		DeliveryInfo deliveryInfo;

		Multimap<String, Module> moduleHash = ArrayListMultimap.create();

		for (DeliveryView deliveryView : noneReleasedDeliveries) {
			List<DeliveryInfo> deliveryInfoList = new ArrayList<DeliveryInfo>();

			deliveryInfo = new DeliveryInfo();

			deliveryInfo.setFunctionality(deliveryView.getFunctionality());
			deliveryInfo.setClient(deliveryView.getClient());
			deliveryInfo.setJira(deliveryView.getTicket());
			deliveryInfo.setType(deliveryView.getType());

			deliveryInfoList.add(deliveryInfo);

			List<ObjectModule> objectModuleList = getJavaObject(deliveryView.getId());
			Module module = new Module();
			module.setNotes(deliveryInfoList);

			for (ObjectModule objectModule : objectModuleList) {

				module.setName(objectModule.getName());
				
				for (JavaObject javaObject:objectModule.getJavaObject().values())
					module.getJavaObjects().add(javaObject);
					
				moduleHash.put(objectModule.getName(), module);

			}
		}



		releaseResponse.setVersion(version);
		for (String key : moduleHash.keySet()) {
			Module module = new Module();

			module.setName(key);

			for (Module tmp : moduleHash.get(key)) {
				
				for (DeliveryInfo deliverIinfo : tmp.getNotes())
					module.getNotes().add(deliverIinfo);
				
					module.setJavaObjects(tmp.getJavaObjects()); 
			}

			releaseResponse.getModules().add(module);
		}

		return releaseResponse;

	}

	private List<ObjectModule> getJavaObject(String deliveryId) {

		List<DeliveryDetails> deliveryDetailList = deliveryDetialsRepo.findByDeliveryId(deliveryId);

		List<ObjectModule> objectModuleList = new ArrayList<ObjectModule>();

		for (DeliveryDetails deliveryDetails : deliveryDetailList) {

			ObjectModule objectModule = new ObjectModule();

			Appl appl = applRepo.findById(deliveryDetails.getApplId()).get();
			SubModule subModule = appl.getSubModule();
			if (subModule != null) {

				JavaObject javaObject = new JavaObject();
				javaObject.setName(subModule.getJavaObj());
				javaObject.setVersion(subModule.getJavaObjVersion());

				objectModule.setName(subModule.getName());
				objectModule.getJavaObject().put(subModule.getJavaObj(),javaObject);

				objectModuleList.add(objectModule);
			}
		}
		return objectModuleList;
	}

}

class ObjectModule {
	private String name;
	private Multimap<String, JavaObject> javaObject ;
	
	public ObjectModule() {
		setJavaObject(ArrayListMultimap.create());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Multimap<String, JavaObject> getJavaObject() {
		return javaObject;
	}

	public void setJavaObject(Multimap<String, JavaObject> javaObject) {
		this.javaObject = javaObject;
	}



}
