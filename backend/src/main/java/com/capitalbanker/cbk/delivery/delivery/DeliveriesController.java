package com.capitalbanker.cbk.delivery.delivery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capitalbanker.cbk.delivery.delivery.model.DeliveryDetails;
import com.capitalbanker.cbk.delivery.delivery.model.DeliveryView;
import com.capitalbanker.cbk.delivery.delivery.repository.DeliveryDetailsObjectRepo;
import com.capitalbanker.cbk.delivery.delivery.repository.DeliveryDetailsRepo;
import com.capitalbanker.cbk.delivery.delivery.repository.DeliveryRepo;
import com.capitalbanker.cbk.delivery.delivery.repository.DeliveryTypeRepo;
import com.capitalbanker.cbk.delivery.delivery.repository.DeliveryViewRepo;
import com.capitalbanker.cbk.delivery.delivery.service.DeliveryDetailsService;
import com.capitalbanker.cbk.delivery.delivery.service.DeliveryService;
import com.capitalbanker.cbk.delivery.delivery.service.DeliveryViewService;
import com.capitalbanker.cbk.delivery.shared.model.CustomIdFullName;
import com.capitalbanker.cbk.delivery.shared.repository.ClientRepo;
import com.capitalbanker.cbk.delivery.shared.repository.UserRepository;
import com.capitalbanker.cbk.delivery.shared.response.ErrorDetailsResponse;
import com.capitalbanker.cbk.delivery.shared.response.ServiceResponseBase;
import com.capitalbanker.cgb.applcontrolssvn.Runner;
import com.capitalbanker.cgb.buisnessobjects.ApplBO;

@RestController
@RequestMapping(value = "/deliveries")
public class DeliveriesController {

	@Autowired
	DeliveryRepo deliveriesRepo;

	@Autowired
	DeliveryViewRepo deliveryViewRepo;

	@Autowired
	DeliveryViewService deliveryViewService;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ClientRepo clientRepo;

	@Autowired
	DeliveryService deliveryService;

	@Autowired
	DeliveryDetailsRepo deliveryDetailsRepo;

	@Autowired
	DeliveryDetailsService deliveryDetailsService;

	@Autowired
	DeliveryTypeRepo deliveryTypeRepo;

	@Autowired
	private DeliveryDetailsObjectRepo deliveryDetailsObjectRepo;

	@RequestMapping(value = "/getType", method = RequestMethod.GET)
	public ServiceResponseBase<List<CustomIdFullName>> getTypes() throws Exception {
		try {
			return new ServiceResponseBase<List<CustomIdFullName>>(deliveryTypeRepo.getTypes(), null);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@RequestMapping(value = "/initialise", method = RequestMethod.GET)
	public ServiceResponseBase<HashMap<String, List<CustomIdFullName>>> initialiseDeliveryScreen(
			@RequestParam String value, @RequestParam String type) throws Exception {
		try {

			List<CustomIdFullName> clients = null;
			List<CustomIdFullName> deliveredby = null;
			List<CustomIdFullName> source = null;

			if (type.equals("client"))
				clients = clientRepo.getClient(value);

			if (type.equals("deliveredBy"))
				deliveredby = userRepo.getDev(value);

			if (type.equals("source"))
				source = userRepo.getConsultant(value);

			HashMap<String, List<CustomIdFullName>> toReturn = new HashMap<String, List<CustomIdFullName>>();

			toReturn.putIfAbsent("clients", clients);
			toReturn.putIfAbsent("deliveredBy", deliveredby);
			toReturn.putIfAbsent("source", source);

			return new ServiceResponseBase<HashMap<String, List<CustomIdFullName>>>(toReturn, null);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ServiceResponseBase<Page<DeliveryView>> customSearch(@RequestBody DeliveryView deliveryView,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int pageSize)
			throws Exception {
		try {

			if (deliveryView == null) {
				return null;
			}

			Page<DeliveryView> deliveryList = this.deliveryViewService.customSearch(deliveryView, page, pageSize);
			if (deliveryList != null) {
				return new ServiceResponseBase<Page<DeliveryView>>(deliveryList, null);
			}

			return new ServiceResponseBase<Page<DeliveryView>>(null, "No Data found");

		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	private List<ApplBO> testApplList(List<String> applList) throws Exception {
		List<ApplBO> applControlledList = new ArrayList<ApplBO>();
		for (String applName : applList) {

			ApplBO applControlled = Runner.controlAppl(applName);

			applControlledList.add(applControlled);

		}

		return applControlledList;
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ServiceResponseBase<String> insert(@RequestBody DeliveryInsertBody body, @RequestParam boolean updateFlag)
			throws Exception {

		try {
			String deliveryId = null;
			String warinig = "";
			boolean update=false;

//			TODO
//			if(body.getDelivery().getNotes()==null || body.getDelivery().getNotes().length()>2000
//					|| body.getDelivery().getAddComment()==null || body.getDelivery().getAddComment().length()>4000 
//					|| body.getDelivery().getClientId()==null || body.getDelivery().getClientId().length()>36 
//					|| body.getDelivery().getReleaseId()==null || body.getDelivery().getNotes().length()>36 
//					|| body.getDelivery().getDeliveredById()==null || body.getDelivery().getDeliveredById().length()>2000 
//					|| body.getDelivery().getTypeById()==null || body.getDelivery().getTypeById().length()>2000 ) {
//				
//				return new ErrorDetailsResponse("Input error", new Date()); 
//			}

			// get controlled appl list
			List<ApplBO> applControlledList = testApplList(body.getDeliveryDetails().getApplList());

			// if any ddl or ins or appl has error stop the delivery
			String error = "";
			if (Runner.testIfApplListHasError(applControlledList)) {
				error += "Errors:";
				for (ApplBO appl : applControlledList) {
					if (appl != null) {
						error += appl.getFileBOErrors() + "\n\n";
					} else {
						error += "doesn’t exist.";
					}
				}
				if (!updateFlag)
					return new ErrorDetailsResponse(error, new Date());
			}

			// if appl has any warning
			if (Runner.testIfApplListHasWarning(applControlledList)) {
				warinig += "\nWarnings:\n";
				for (ApplBO appl : applControlledList) {
					if (appl != null) {
						warinig += appl.getFileBOWarnings() + "\n";
					}
				}
			}

			if (body.getDelivery().getId() == null) {
				deliveryId = UUID.randomUUID().toString();

				body.getDelivery().setId(deliveryId);

				body.getDeliveryDetails().setDeliveryId(deliveryId);
			} else {

				deliveryId = body.getDelivery().getId();
				update = true;
			}

			deliveryService.insert(body.getDelivery(), updateFlag);

			List<DeliveryDetails> oldDeliveryDetailsIfExists = deliveryDetailsRepo.findByDeliveryId(deliveryId);

			deliveryDetailsService.deleteDeliveryDetailsAndObjects(deliveryId, oldDeliveryDetailsIfExists);

			for (int i = 0; i < applControlledList.size(); i++) {

				ApplBO appl = applControlledList.get(i);
				DeliveryDetails temp = body.getDeliveryDetails();

				temp.setDeliveryId(deliveryId);
				deliveryDetailsService.insert(temp, appl, oldDeliveryDetailsIfExists,updateFlag);

			}
//			if (updateFlag)
				return new ServiceResponseBase<String>("Successfully", error + warinig);
//			else
//				return new ServiceResponseBase<String>("Successfully", warinig);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ServiceResponseBase<String> delete(@RequestParam String deliveryId) throws Exception {
		try {
//			deliveryDetailsContentService.deleteDeliveryDetailsContentByDeliveryId(deliveryId);
			List<String> deliveryDetailsIdList = deliveryDetailsRepo.findIdByDeliveryId(deliveryId);

			for (String deliveryDetailsId : deliveryDetailsIdList) {
				deliveryDetailsObjectRepo.deleteByDeliveryDetailsId(deliveryDetailsId);
			}
			deliveryDetailsRepo.deleteByDeliveryId(deliveryId);
			this.deliveriesRepo.deleteById(deliveryId);

			return new ServiceResponseBase<String>("Delivery Deleted", null);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

//	@RequestMapping(value = "/all", method = RequestMethod.GET)
//	public Page<String> fingAll(@RequestParam int page) {
//
//		return deliveryViewRepo.getDeliveredByPag("el", PageRequest.of(page, 5));
//		// return deliveriesRepo.findAll(PageRequest.of(page,5));
//	}
//
//	@RequestMapping(value = "/1", method = RequestMethod.GET)
//	public List<DeliveryDetails> findById() {
//
//		return deliveryDetailsRepo.findAll();
//
//	}

//	@RequestMapping(value = "/client", method = RequestMethod.GET)
//	public ServiceResponseBase<List<DeliveryView>> findByClient() {
//
//		return new ServiceResponseBase<List<DeliveryView>>(deliveryViewRepo.findBy("Elio Khattar", "NEW"), null);
//	}
//
//	@RequestMapping(value = "/note", method = RequestMethod.POST)
//	public Collection<Delivery> findByNote(@RequestBody String note) {
//
//		return deliveriesRepo.findByNotesContaining(note);
//	}
//
//	@RequestMapping(value = "/view", method = RequestMethod.GET)
//	public ServiceResponseBase<List<DeliveryView>> getAll() {
//
//		return new ServiceResponseBase<List<DeliveryView>>(this.deliveryViewService.findDelivery("Khaled Chehab"),
//				null);
//	}

//	@RequestMapping(value = "/delivery", method = RequestMethod.POST)
//	public ServiceResponseBase<List<DeliveryView>> getDelivery(@RequestBody DeliveryView delv) {
//
//		List<DeliveryView> delivery = this.deliveryViewService.findDelivery(delv.getFunctionality());
//
//		return new ServiceResponseBase<List<DeliveryView>>(delivery, null);
//	}

//	@RequestMapping(value = "/deliveredBy")
//	public ServiceResponseBase<List<String>> getDeliveredBy() {
//		return new ServiceResponseBase<List<String>>(
//				deliveryViewRepo.getDeliveredBy("").stream().sorted().collect(Collectors.toList()), null);
//
//	}
}