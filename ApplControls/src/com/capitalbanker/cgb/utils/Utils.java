package com.capitalbanker.cgb.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import com.capitalbanker.cgb.connection.SFTPConnection;
import com.capitalbanker.cgb.constants.ProjectConstants;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;

public class Utils {

	public static String getRandomUUID() {
		return UUID.randomUUID().toString();
	}

	public static String ListToString(List<String> list) {
		StringBuilder str = new StringBuilder();

		// for existing control
		if (list == null) {
			return "";
		}
		for (String s : list) {
			str.append(s + "\n");
		}

		return str.toString();
	}

	

	public static Properties getPropertiesFile() throws IOException {

		Properties properties = new Properties();

//		for reffering for conf file location 
//		ProjectConstants.configPath+ ProjectConstants.FILE_SEPERATOR+
		String properties_path = "config.properties";
		FileInputStream ip = new FileInputStream(properties_path);
		properties.load(ip);

		return properties;
	}

	

	public static boolean isRunningLogOn() {
		try {
			if (Utils.getPropertiesFile().getProperty("RunningLog").equals("on"))
				return true;
		} catch (Exception e) {
			return true;
		}

		return false;
	}

	public static void printProgress(String msg) {
		if (isRunningLogOn())
			System.out.print(msg);
	}

	public static String getType(String name) {
		if (name.endsWith(".appl")) {
			return "appl";
		} else if (name.endsWith(".ddl")) {
			return "ddl";
		} else if (name.endsWith(".ins") || name.endsWith(".sda")) {
			return "ins";
		} else if (name.endsWith(".sql")) {
			return "sql";
		} else if (name.endsWith(".war") || name.endsWith(".java") || name.endsWith(".class")) {
			return "java";
		} else if (name.endsWith(".sqi")) {
			return "sqr";
		} else if (name.endsWith(".ctl")) {
			return "ctl";
		} else if (name.endsWith(".sh")) {
			return "sh";
		} else if (name.endsWith(".doc")) {
			return "doc";
		} else if (name.endsWith(".dmp")) {
			return "dmp";
		} else if (name.endsWith(".fmb") || name.endsWith(".fmx")) {
			return "fmb";
		} else if (name.endsWith(".rdf")) {
			return "rpt";
		}

		return "err";
	}



	public static String downloadFile(String fileName, String sourcePath) throws IOException, JSchException {

		String destPath = getPropertiesFile().getProperty("TEMP_PATH");

		File destFile = new File(destPath);

		ChannelSftp client = SFTPConnection.getClient();
//		FTPClient client = FTPConnection.getClient();
		String msg = SFTPConnection.downloadFile(client, sourcePath, fileName, destFile).toString();

		
		return msg;
	}

	public static boolean existOnSara6(String fileName) throws IOException, JSchException {

		String ftpCapbPath = getPropertiesFile().getProperty("ftp_capb_path");
		String ftpTstPath = getPropertiesFile().getProperty("ftp_tst_path");
		String ftpBnqPath = getPropertiesFile().getProperty("ftp_bnq_path");

		String msgCapb = downloadFile(fileName, ftpCapbPath);

		Utils.printProgress("Checking CAPB_TST");
		if (!msgCapb.contains("Error")) {
			SFTPConnection.disconnect();
			return true;
		}
		Utils.printProgress("Checking TST");
		String msgTst = downloadFile(fileName, ftpTstPath);
		if (!msgTst.contains("Error")) {
			SFTPConnection.disconnect();
			return true;
		}
		String msgBnq = downloadFile(fileName, ftpBnqPath);

		Utils.printProgress("Checking BNQ");
		if (!msgBnq.contains("Error")) {
			SFTPConnection.disconnect();
			return true;
		}
		SFTPConnection.disconnect();
		return false;

	}

	

	public static String getSubModuleId(String module) {
		String moduleId = ProjectConstants.moduleId.get(module);

		return (moduleId == null) ? ProjectConstants.moduleId.get("UNKNOWN") : moduleId;

	}

	
}