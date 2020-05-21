package com.capitalbanker.cbk.delivery.delivery.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitalbanker.cbk.delivery.appl.service.ApplService;
import com.capitalbanker.cbk.delivery.delivery.model.DeliveryDetails;
import com.capitalbanker.cbk.delivery.delivery.repository.DeliveryDetailsObjectRepo;
import com.capitalbanker.cbk.delivery.delivery.repository.DeliveryDetailsRepo;
import com.capitalbanker.cgb.buisnessobjects.ApplBO;
import com.capitalbanker.cgb.utils.Utils;

@Service
@Transactional
public class DeliveryDetailsService {

	@Autowired
	private ApplService applService;

	@Autowired
	private DeliveryDetailsRepo deliveryDetailsRepo;

	@Autowired
	private DeliveryDetailsObjectRepo deliveryDetailsObjectRepo;

	public void insert(DeliveryDetails deliveryDetails, ApplBO appl, List<DeliveryDetails> oldDeliveryDetailsIfExists,boolean updateFlag)
			throws Exception {

		
		applService.insertApplAndDetailsAndWar(appl, deliveryDetails, oldDeliveryDetailsIfExists, true, updateFlag, null,
				null);

	}

	public void deleteDeliveryDetailsAndObjects(String deliveryId, List<DeliveryDetails> oldDeliveryDetailsIfExists) {

		for (DeliveryDetails tmp : oldDeliveryDetailsIfExists) {
			deliveryDetailsObjectRepo.deleteByDeliveryDetailsId(tmp.getId());
		}
		deliveryDetailsRepo.deleteByDeliveryId(deliveryId);
	}

}
