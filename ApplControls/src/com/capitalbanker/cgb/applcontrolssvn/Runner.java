package com.capitalbanker.cgb.applcontrolssvn;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.capitalbanker.cgb.builder.ApplToJavaBuilder;
import com.capitalbanker.cgb.buisnessobjects.ApplBO;
import com.capitalbanker.cgb.constants.ProjectConstants;
import com.capitalbanker.cgb.utils.Utils;
import com.jcraft.jsch.JSchException;

public class Runner {

	public static void main(String[] args) throws Exception {
		controlAppl(args[0]);

		return;
	}

	public Runner(String conf) {

	}

	public static ApplBO controlAppl(String fileName) throws IOException, JSchException, ClassNotFoundException,
			SQLException, InterruptedException, ParseException {

		boolean hasError = true;
		boolean recursive = true;
		ApplBO applBOToControl = new ApplBO();
		applBOToControl.setName(fileName);

		String tmpPath = Utils.getPropertiesFile().getProperty("TEMP_PATH") + ProjectConstants.FILE_SEPERATOR + "appl";

		System.out.println("\nCONTROLS FOR " + fileName + " STARTED\n");

		if (!fileName.equals(fileName.toLowerCase())) {
			applBOToControl.getErrors().add((fileName + ":\nError : File name contain upercase characters"));

			applBOToControl.getFileBOErrors().append((fileName + ":\nError : File name contain upercase characters"));
			Utils.printProgress(" \nCONTROLS FOR " + fileName + " ENDED WITH ERRORS\n\n");
			return applBOToControl;
		}

		if (!fileName.endsWith(".appl")) {
			applBOToControl.getErrors().add((fileName + ": Invalid Extention"));

			applBOToControl.getFileBOErrors().append((fileName + ": Invalid Extention"));
			Utils.printProgress(" \nCONTROLS FOR " + fileName + " ENDED WITH ERRORS\n\n");
			return applBOToControl;
		}

		if (!Utils.existOnSara6(fileName)) {
			applBOToControl.getErrors().add(("File: " + fileName + " doesn't exist.\n"));

			applBOToControl.getFileBOErrors().append(("File: " + fileName + " doesn't exist.\n"));
			Utils.printProgress(" \nCONTROLS FOR " + fileName + " ENDED WITH ERRORS\n\n");
			return applBOToControl;
		}

		ProjectConstants.runId = Utils.getRandomUUID().replaceAll("-", "");

		ApplToJavaBuilder convert = new ApplToJavaBuilder(tmpPath, fileName);

		Utils.printProgress("Building " + fileName + "...");

		applBOToControl = convert.build(recursive);

		Utils.printProgress("Running Controls...");

		applBOToControl.controlAppl();
		Utils.printProgress("Done...\n\n");

		// set up the error and warinig string builders
		applBOToControl.getErrorsIfExist();

		applBOToControl.getWarningIfExists();

//		

		if (applBOToControl.hasAnyWarning())
			System.out.println("Warning:" + applBOToControl.getFileBOWarnings().toString());

		hasError = applBOToControl.hasAnyError();

		

		if (hasError) {
			System.out.println("\nError:" + applBOToControl.getFileBOErrors().toString());

			System.out.println(" \nCONTROLS FOR " + fileName + " ENDED WITH ERRORS\n\n");
		} else {
			System.out.println("\nCONTROLS FOR " + fileName + " ENDED WITHOUT ERRORS\n");
		}
		
		if(Utils.getPropertiesFile().getProperty("DELETE_TEMP").equals("true"))
		FileUtils.deleteDirectory(new File(Utils.getPropertiesFile().getProperty("TEMP_PATH")));

		return applBOToControl;
	}

	public static boolean testIfApplListHasError(List<ApplBO> list) {

		for (ApplBO appl : list) {

			if (appl.hasAnyError()) {
				return true;
			}
		}
		return false;
	}

	public static boolean testIfApplListHasWarning(List<ApplBO> list) {

		for (ApplBO appl : list) {
			if (appl.hasAnyWarning()) {
				return true;
			}
		}
		return false;
	}

}
