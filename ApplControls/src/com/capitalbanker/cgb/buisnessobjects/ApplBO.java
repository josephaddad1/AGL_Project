package com.capitalbanker.cgb.buisnessobjects;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.capitalbanker.cgb.constants.ProjectConstants;
import com.capitalbanker.cgb.utils.Utils;
import com.jcraft.jsch.JSchException;

public class ApplBO extends FileBO {

	private String id;
	private Date releaseDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String source;
	private String date;
	private List<FileBO> fileNameList;
	private List<ApplBO> recursiveAppl;

	public ApplBO(String name, String path) throws ClassNotFoundException, IOException {
		super(name, path);
		fileNameList = new ArrayList<>();
		recursiveAppl = new ArrayList<>();

	}

	@Override
	public String getType() {
		return "appl";
	}

	public ApplBO() {
		super();

		fileNameList = new ArrayList<>();
		recursiveAppl = new ArrayList<>();
	}

	public Boolean isCapbTst() {
		return ProjectConstants.APPL_SOURCE_CAPBTST.equalsIgnoreCase(this.source);
	}

	public Boolean isTst() {
		return ProjectConstants.APPL_SOURCE_TST.equalsIgnoreCase(this.source);
	}

	public Boolean isBNQ() {
		return ProjectConstants.APPL_SOURCE_BNQ.equalsIgnoreCase(this.source);
	}

	public boolean isParent() {
		if (this.name.startsWith("maj_"))
			return false;

		return true;
	}

	public void controlAppl()
			throws SQLException, IOException, InterruptedException, ClassNotFoundException, JSchException {

		if (lineList != null && !lineList.isEmpty()) {
			// this appl control
			if (containBackSlash()) {
				errors.add(" Format-Error     : APPL contains backslash");
			}
			if (!checkFooter())
				errors.add(" Format-Error     : Footer isn't standard");

			String msg;
			if ((msg = checkHeaderAppl()).equals("source_error"))
				errors.add(" Format-Error     : Error in Source!!");
			else if (!msg.equals("valid"))
				errors.add(" Format-Error     : Header isn't standard");

			// if pks and pkb exits
			checkPkbWithoutPks();

			// adding errors in this function to specify with file is missing

			// fileBOList controls

		
			
			for (int i = 0; i < this.fileNameList.size(); i++) {

				FileBO fileBO = fileNameList.get(i);

				if (fileBO != null) {

					if (Utils.getType(fileBO.getName()).equals("ins") || Utils.getType(fileBO.getName()).equals("ddl"))
						fileBO.control();

				} else {

				}
			}
		}
		for (ApplBO appl : recursiveAppl) {
			appl.controlAppl();
		}

	}

	
	
	

	// Checks if an APPL file Contains pkb and pks
	// Else if one of them missing returns warning
	private void checkPkbWithoutPks() {
		List<DdlBO> ddlBOList = getDdlBOList();

		String ddlListToString = "";

		for (DdlBO ddl : ddlBOList) {
			ddlListToString += ddl.getName();

		}

		for (DdlBO ddlBO : ddlBOList) {
			String ddlBOName = ddlBO.getName();

			if (ddlBOName.startsWith("pks_")) {
				String name = ddlBOName.replace("pks_", "");

				if (!ddlListToString.contains("pkb_" + name)) {
					warnings.add(" Missing-Error    : PKB_" + name.toUpperCase() + " IS MISSING!!  note: PKS_" + name
							+ " Exists");

				}
			} else if (ddlBOName.startsWith("pkb_")) {
				String name = ddlBOName.replace("pkb_", "");

				if (!ddlListToString.contains("pks_" + name)) {
					warnings.add(" Missing-Error    : PKS_" + name.toUpperCase() + " IS MISSING!!  note: PKB_" + name
							+ " Exists");

				}
			}
		}

	}

	// read ddl and ins files from the appl and write them in a shell file

	// executing the shell files that executes ddl and ins files
	// String fileToExecute = INS/DDL
	
	// applName is the main appl name to save all sub-appls in this file

	private boolean containBackSlash() {

		if (linesString.contains("\\"))
			return true;
		return false;

	}

	public String checkHeaderAppl() {

		if (super.checkHeader()) {
			if (!isRegularExpressionFound(ProjectConstants.source_reg_expression, linesString)) {
				return "source_error";
			}
		} else
			return "error";

		return "valid";
	}

	public boolean FileBOListIsEmpty() {
		return fileNameList.isEmpty();
	}

	public void addFileBO(FileBO fileBO) {

		fileNameList.add(fileBO);
	}

	public List<DdlBO> getDdlBOList() {

		List<DdlBO> result = new ArrayList<DdlBO>();
		for (FileBO fb : fileNameList) {
			if (fb instanceof DdlBO)
				result.add((DdlBO) fb);
		}
		return result;
	}

	public boolean hasAnyError() {

		if (this.hasErrors()) {
			return true;
		} else {

			for (ApplBO applBO : recursiveAppl) {
//				if (applBO.hasErrors())
//					return true;

				return (applBO.hasAnyError());
			}

			for (FileBO fileBO : fileNameList) {
				if (fileBO.hasErrors()) {
					return true;
				}
			}

			return false;
		}

	}

	public boolean hasAnyWarning() {

		if (this.hasWarning()) {
			return true;
		} else {

			for (ApplBO applBO : recursiveAppl) {

				return (applBO.hasAnyWarning());
			}

			for (FileBO fileBO : fileNameList) {
				if (fileBO.hasWarning()) {
					return true;
				}
			}

			return false;
		}

	}

	public String getErrorsIfExist() {

		// recursive condition is in for condition (if errors list is empty the
		// function
		// ends)

		if (this.hasErrors())
			fileBOErrors.append(name + ":");
//			System.out.println("\n" + name + ":");

		// print the main appl log
		for (String mainApplLog : this.errors) {
			fileBOErrors.append( mainApplLog);
//			System.out.println(mainApplLog);
		}

		// print all BOs log
		for (ApplBO applBO : recursiveAppl) {
			fileBOErrors.append("\n" + applBO.getErrorsIfExist());
		}

		for (FileBO fileBO : fileNameList) {
			if (fileBO.hasErrors())
				fileBOErrors.append("\n" + fileBO.getErrorString());
		}

		return fileBOErrors.toString();

	}

	public String getWarningIfExists() {

		// recursive condition is in for condition (if errors list is empty the
		// function
		// ends)

		if (this.hasWarning())
			fileBOWarnings.append(name + ":");
//			System.out.println("\n" + name + ":");

		// print the main appl log

		for (String warning : this.warnings) {
			fileBOWarnings.append("\n" + warning);
//			System.out.println(warning);
		}

		// print all BOs log
		for (ApplBO applBO : recursiveAppl) {
			fileBOWarnings.append("\n" + applBO.getWarningIfExists());
		}

		for (FileBO fileBO : fileNameList) {
			if (fileBO.hasWarning())

				fileBOWarnings.append("\n" + fileBO.getWarningsString());
		}

		return fileBOWarnings.toString();

	}

	public List<ApplBO> getRecursiveAppl() {
		return recursiveAppl;
	}

	public void setRecursiveAppl(List<ApplBO> recursiveAppl) {
		this.recursiveAppl = recursiveAppl;
	}

	// Getters and Setters

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<FileBO> getFileNameList() {
		return fileNameList;
	}

	public void setFileNameList(List<FileBO> fileBOList) {
		this.fileNameList = fileBOList;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

}
