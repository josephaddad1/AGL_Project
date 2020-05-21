package com.capitalbanker.cbk.delivery.delivery.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitalbanker.cbk.delivery.delivery.model.Delivery;
import com.capitalbanker.cbk.delivery.delivery.repository.DeliveryRepo;

@Service
@Transactional
public class DeliveryService {

	@Autowired
	private DeliveryRepo deliveryRepo;

	public void insert(Delivery delivery, boolean update) throws Exception {

//		Date date=new SimpleDateFormat("dd-MMM-yyyy").parse(delivery.getCreatedDate());  

		Date now = new Date();
		if (update) {
			delivery.setUpdatedDate(now);
		} else {
			delivery.setCreatedDate(now);
		}
		deliveryRepo.save(delivery);
	}

}
