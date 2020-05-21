package com.capitalbanker.cbk.delivery.delivery.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.capitalbanker.cbk.delivery.delivery.model.DeliveryView;
import com.capitalbanker.cbk.delivery.delivery.repository.DeliveryViewRepo;

@Service
@Transactional
public class DeliveryViewService {

	@Autowired
	private DeliveryViewRepo deliveryViewRepo;

	public Page<DeliveryView> customSearch(DeliveryView deliveryView, int page, int pageSize) throws Exception {
		try {

			// if the object null to prevent getting all the data from the database due to
			// the select query with is null condition
			if (deliveryView.getId() == null && deliveryView.getFunctionality() == null
					&& deliveryView.getType() == null && deliveryView.getClient() == null
					&& deliveryView.getSource() == null && deliveryView.getTicket() == null
					&& deliveryView.getAppl() == null && deliveryView.getDeliveredBy() == null
					&& deliveryView.getReleaseDate() == null && deliveryView.getApprv() == null
					&& deliveryView.getComments() == null && deliveryView.getIncApp() == null) {
				return null;
			}
			if (deliveryView.getToDate() == null) {
				deliveryView.setToDate(new Date());
			}

			// to prevent passing "" to like function for the functionality (note)
			if (deliveryView.getFunctionality() == null || deliveryView.getFunctionality().equals("")) {

				return deliveryViewRepo.customSearchFuncNull(deliveryView.getId(), deliveryView.getType(),
						deliveryView.getClient(), deliveryView.getSource(), deliveryView.getTicket(),
						deliveryView.getAppl(), deliveryView.getDeliveredBy(), deliveryView.getReleaseDate(),
						deliveryView.getToDate(), deliveryView.getApprv(), deliveryView.getComments(),
						deliveryView.getIncApp(), PageRequest.of(page, pageSize));

			} else {

				System.out.println("not null " + deliveryView.getDeliveredBy() + " " + deliveryView.getReleaseDate());
				return deliveryViewRepo.customSearchFuncNotNull(deliveryView.getId(), deliveryView.getFunctionality(),
						deliveryView.getType(), deliveryView.getClient(), deliveryView.getSource(),
						deliveryView.getTicket(), deliveryView.getAppl(), deliveryView.getDeliveredBy(),
						deliveryView.getReleaseDate(), deliveryView.getToDate(), deliveryView.getApprv(),
						deliveryView.getComments(), deliveryView.getIncApp(), PageRequest.of(page, pageSize));
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
