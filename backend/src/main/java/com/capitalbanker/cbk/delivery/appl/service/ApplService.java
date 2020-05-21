package com.capitalbanker.cbk.delivery.appl.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.apache.commons.validator.routines.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capitalbanker.cbk.delivery.appl.model.Appl;
import com.capitalbanker.cbk.delivery.appl.model.ApplContent;
import com.capitalbanker.cbk.delivery.appl.repository.ApplContentRepo;
import com.capitalbanker.cbk.delivery.appl.repository.ApplRepo;
import com.capitalbanker.cbk.delivery.delivery.model.DeliveryDetails;
import com.capitalbanker.cbk.delivery.delivery.model.DeliveryDetailsObject;
import com.capitalbanker.cbk.delivery.delivery.repository.DeliveryDetailsObjectRepo;
import com.capitalbanker.cbk.delivery.delivery.repository.DeliveryDetailsRepo;
import com.capitalbanker.cbk.delivery.modules.model.SubModule;
import com.capitalbanker.cbk.delivery.modules.repository.SubModuleRepo;
import com.capitalbanker.cbk.delivery.shared.repository.FileBOTypeRepo;
import com.capitalbanker.cbk.delivery.shared.utils.StringUtils;
import com.capitalbanker.cgb.applcontrolssvn.Runner;
import com.capitalbanker.cgb.buisnessobjects.ApplBO;
import com.capitalbanker.cgb.buisnessobjects.FileBO;
import com.capitalbanker.cgb.buisnessobjects.JavaBO;
import com.capitalbanker.cgb.utils.Utils;
import com.jcraft.jsch.JSchException;

import javassist.NotFoundException;

@Service
public class ApplService {

	@Autowired
	ApplRepo applRepo;

	@Autowired
	SubModuleRepo subModuleRepo;

	@Autowired
	private FileBOTypeRepo fileBORepo;

	@Autowired
	ApplContentRepo applContentRepo;

	@Autowired
	private DeliveryDetailsObjectRepo deliveryDetailsObjectRepo;

	@Autowired
	private DeliveryDetailsRepo deliveryDetailsRepo;

	public List<Appl> getApplIn(String name) {

		List<String> applNames = StringUtils.constructListFromCommaSeparatedElement(name);

		return applRepo.findByName(applNames);
	}

	public List<Appl> applCustomSearch(Appl appl) throws ParseException {

		if (appl == null || (appl.getErrorFlag() == null && appl.getToDate() == null && appl.getApplDate() == null
				&& appl.getName() == null && appl.getSubModuleId() == null && appl.getContentName() == null
				&& appl.getModuleId() == null))
			return null;

		if (appl.getName() != null) {

			if (appl.getName().trim().isEmpty())
				appl.setName(null); 
			else {
				appl.setName(appl.getName().toLowerCase());
			}
		}

		return applRepo.applCustomSearch(appl.getName(), appl.getModuleId(), appl.getSubModuleId(), appl.getApplDate(),
				appl.getToDate(), appl.getErrorFlag());
	}

	public ApplBO insertApplAndDetailsAndWar(ApplBO buildedAppl, DeliveryDetails deliveryDetails,
			List<DeliveryDetails> oldDeliveryDetailsIfExists, boolean insertDeliveryDetails, boolean updateApplFlag,
			String applId, String updatedById) throws ParseException, ClassNotFoundException, IOException,
			JSchException, SQLException, InterruptedException {

		String id = null;
		String updatedBy = null;
		String createdBy = null;
		Date createdDate = null;
		Date updatedDate = null;

		boolean updateFlag = false;
		boolean deliveryDetailsExists = false;
		Appl oldAppl = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		if (updateApplFlag && applId != null) {
			oldAppl = applRepo.findById(applId).get();
			if (oldAppl != null) {
				buildedAppl = Runner.controlAppl(oldAppl.getName());
			}

		} else {
			oldAppl = applRepo.findByName(buildedAppl.getName());
		}
//		insert
		if (oldAppl == null) {
			id = Utils.getRandomUUID();
			Date now = new Date();
			createdDate = sdf.parse(sdf.format(now));
			createdBy = deliveryDetails == null ? updatedById : deliveryDetails.getCreatedBy();

		}
//		update
		else {
			id = oldAppl.getId();
			Date now = new Date();

			for (DeliveryDetails tmp : oldDeliveryDetailsIfExists) {

				if (tmp.getApplId().equals(oldAppl.getId())) {
					deliveryDetails.setId(tmp.getId());
					deliveryDetails.setCreatedDate(sdf.parse(tmp.getCreatedDate()));
					;
					deliveryDetails.setUpdatedDate(sdf.parse(sdf.format(now)));
					deliveryDetailsExists = true;
				}
			}

			if (!deliveryDetailsExists && deliveryDetails != null) {
				deliveryDetails.setId(Utils.getRandomUUID());
				deliveryDetails.setCreatedDate(sdf.parse(sdf.format(now)));
			}

			updatedBy = updatedById == null ? deliveryDetails.getUpdatedBy() : updatedById;
			createdBy = oldAppl.getCreatedBy();
			createdDate = sdf.parse(oldAppl.getCreatedDate());

			updatedDate = sdf.parse(sdf.format(now));

			updateFlag = true;
		}

		Appl appl = new Appl();

		String applSubModuleId = "";
		String applModuleName = "";
		applModuleName = Utils.getModule(buildedAppl.getName());

		applSubModuleId = subModuleRepo.findIdByAppl(applModuleName);

		if (applSubModuleId == null) {
			applSubModuleId = subModuleRepo.findIdByAppl("UNKNOWN");
		}
		buildedAppl.setId(id);
		appl.setId(id);
		appl.setName(buildedAppl.getName());

		DateValidator validator = new DateValidator();
		Date temp = null;

		temp = validator.validate(buildedAppl.getDate().replace(".", "/"), "dd/MM/yyyy");
		if (temp == null)
			temp = validator.validate(buildedAppl.getDate());

		appl.setApplDate(temp);
		appl.setSource(buildedAppl.getSource());
		appl.setSubModuleId(applSubModuleId);
		appl.setErrorFlag(buildedAppl.hasAnyError() ? "O" : "N");
		appl.setErrorMessage(buildedAppl.getFileBOErrors().toString());
		appl.setCreatedBy(createdBy);
		appl.setUpdatedBy(updatedBy);

		appl.setCreatedDate(createdDate);
		appl.setUpdatedDate(updatedDate);

		applRepo.save(appl);

		if (insertDeliveryDetails) {

			if (deliveryDetails.getId() == null) {
				deliveryDetails.setId(Utils.getRandomUUID());
			}

			if (deliveryDetails.getCreatedDate() == null)
				deliveryDetails.setCreatedDate(createdDate);
			else
				deliveryDetails.setUpdatedDate(createdDate);

			deliveryDetails.setApplId(id);

			deliveryDetailsRepo.save(deliveryDetails);
		}

		// saving content
		boolean exist = false;
		if (updateFlag) {
			for (DeliveryDetails oldDeliveryDetails : oldDeliveryDetailsIfExists) {
				if (oldDeliveryDetails.getApplId().equals(id)) {
					exist = true;
				}
			}
			applContentRepo.deleteByApplId(id);
		}
		String deliveryDetailsId = deliveryDetails == null ? null : deliveryDetails.getId();

		for (FileBO fileBO : buildedAppl.getFileNameList()) {

			saveApplContent(fileBO, null, deliveryDetailsId, id, exist, updateApplFlag);
		}

		for (ApplBO applBOrecursive : buildedAppl.getRecursiveAppl()) {

			insertApplAndDetailsAndWar(applBOrecursive, deliveryDetails, oldDeliveryDetailsIfExists, false,
					updateApplFlag, null, updatedById);

			String parentId = saveApplContent(applBOrecursive, null, deliveryDetailsId, id, exist, updateApplFlag);

			for (FileBO fileBO : applBOrecursive.getFileNameList()) {

				saveApplContent(fileBO, parentId, deliveryDetailsId, null, exist, updateApplFlag);
			}
		}

		return buildedAppl;
	}

	public String saveApplContent(FileBO fileBO, String parentId, String deliveryDetailsId, String applId,
			boolean exist, boolean updateApplFlag) {

		if (fileBO instanceof JavaBO) {

			String newVersion = null;
			String version = null;

//			 = false;
//			for (ApplContent tmp : existingObjects) {
//				
//				if (tmp.getObjectName().equals(fileBO.getName())){
//				exist = true;
//				break;}
//			}

			SubModule module = subModuleRepo
					.javaObjectVersion(fileBO.getName().substring(0, fileBO.getName().length() - 4));
			if (module != null) {
				version = module.getJavaObjVersion();
			}
			if (!exist && !updateApplFlag) {
				if (version != null) {
					newVersion = "";
					version = version.replace(" ", "");
					String chars[] = version.split("\\.");
					if (chars.length == 3) {
						version += ".1";
						newVersion = version;
					} else {

						for (int i = 0; i < chars.length; i++) {
							newVersion += chars[i] + ".";
							if (i == chars.length - 2) {

								String inc = Integer.toString((Integer.parseInt(chars[i + 1]) + 1));
								newVersion += inc;

								break;
							}
						}
					}
					module.setJavaObjVersion(newVersion);
					subModuleRepo.save(module);
					fileBO.setVersion(newVersion);
				} else {
					fileBO.setVersion("1.0.0");
				}

			} else {
				fileBO.setVersion(version);
			}
			if (deliveryDetailsId != null) {
				DeliveryDetailsObject tmp = new DeliveryDetailsObject();

				tmp.setId(Utils.getRandomUUID());
				tmp.setVersion(fileBO.getVersion());
				tmp.setObjectName(fileBO.getName());
				tmp.setDeliveryDetailsId(deliveryDetailsId);

				deliveryDetailsObjectRepo.save(tmp);
			}
		}

		// increasing version for war files
		String id = Utils.getRandomUUID();
		ApplContent applContent = new ApplContent();
		applContent.setId(id);
		applContent.setObjectName(fileBO.getName());
		applContent.setVersion(fileBO.getVersion());

		String type = fileBO.getType().toUpperCase();
		String typeId = fileBORepo.findIdByCode(type);

		applContent.setObjectTypeId(typeId);

		applContent.setApplId(applId);

		applContent.setParentId(parentId);

		applContentRepo.save(applContent);

		return id;

	}

	public ApplBO updateAppl(String applId, String updatedById) throws ClassNotFoundException, ParseException,
			IOException, JSchException, SQLException, InterruptedException, NotFoundException {
		return insertApplAndDetailsAndWar(null, null, new ArrayList<DeliveryDetails>()

				, false, true, applId, updatedById);

	}

}
