package com.capitalbanker.cgb.connection;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.capitalbanker.cgb.constants.ProjectConstants;
import com.capitalbanker.cgb.utils.Utils;

import static com.capitalbanker.cgb.constants.ProjectConstants.FILE_SEPERATOR;

public class FTPConnection {
	private static FTPClient ftp;
	private static OutputStream outputStream;
	private static boolean success;
	private static int replyCode;

	private static StringBuilder msg = new StringBuilder("");

	private FTPConnection() throws IOException {

	}

	public static FTPClient getClient() throws SocketException, IOException {
		if (ftp == null) {
			ftp = new FTPClient();
			FTPConnect();

		}
		return ftp;

	}

	private static void FTPConnect() throws SocketException, IOException {
		ftp.connect(Utils.getPropertiesFile().getProperty("ftp_server"));

		replyCode = ftp.getReplyCode();

		if (!FTPReply.isPositiveCompletion(replyCode)) {
			Utils.printProgress(
					" Ftp-Error        : The operation was not successful for some reasons, the server refuses or rejects the requested operation\n");
			return;
		}
		success = ftp.login(Utils.getPropertiesFile().getProperty("ftp_username"),
				Utils.getPropertiesFile().getProperty("ftp_password"));

		if (!success) {
			Utils.printProgress(" Ftp-Error        : Could not login to the server or An Error Occured\n");
			return;
		}

	}

	public static StringBuilder downloadFile(FTPClient client, String path, String fileName, File NewApplFolder)
			throws IOException, FileNotFoundException {
		msg = new StringBuilder("");
		success = false;

		if (/* client.isConnected() */true) {

			if (!NewApplFolder.exists())
				NewApplFolder.mkdirs();
		}

		String destPath = NewApplFolder.getPath();

		if (fileName.endsWith(".appl")) {
			NewApplFolder = new File(destPath + ProjectConstants.FILE_SEPERATOR + "appl");
			NewApplFolder.mkdir();
			path += "/appl";
		} else if (fileName.endsWith(".ddl")) {

			NewApplFolder = new File(destPath + ProjectConstants.FILE_SEPERATOR + "ddl");
			NewApplFolder.mkdir();
			path += "/ddl";
		} else if (fileName.endsWith(".ins") || fileName.endsWith(".sda")) {

			NewApplFolder = new File(destPath + ProjectConstants.FILE_SEPERATOR + "ins");
			NewApplFolder.mkdir();
			path += "/ins";
		} else if (fileName.endsWith(".war") || fileName.endsWith(".java") || fileName.endsWith(".class")) {
			// skip war files
			return msg;
		} else if (fileName.endsWith(".sql")) {

			NewApplFolder = new File(destPath + ProjectConstants.FILE_SEPERATOR + "sql");
			NewApplFolder.mkdir();
			path += "/sql";
		} else if (fileName.endsWith(".rdf")) {

			NewApplFolder = new File(destPath + ProjectConstants.FILE_SEPERATOR + "rpt");
			NewApplFolder.mkdir();
			path += "/rpt60";
		} else if (fileName.endsWith(".fmb") || fileName.endsWith(".fmx")) {

			NewApplFolder = new File(destPath + ProjectConstants.FILE_SEPERATOR + "fmb");
			NewApplFolder.mkdir();
			path += "/fmb60";
		} else if (fileName.endsWith(".sqi")) {

			NewApplFolder = new File(destPath + ProjectConstants.FILE_SEPERATOR + "sqr");
			NewApplFolder.mkdir();
			path += "/sqr";
		} else if (fileName.endsWith(".ctl")) {

			NewApplFolder = new File(destPath + ProjectConstants.FILE_SEPERATOR + "ctl");
			NewApplFolder.mkdir();
			path += "/ctl";
		} else if (fileName.endsWith(".sh")) {

			NewApplFolder = new File(destPath + ProjectConstants.FILE_SEPERATOR + "sh");
			NewApplFolder.mkdir();
			path += "/sh";
		}

		String path1 = NewApplFolder.getAbsolutePath() + FILE_SEPERATOR + fileName;
		File newFile = new File(path1);

		outputStream = new BufferedOutputStream(new FileOutputStream(newFile));


		outputStream = new BufferedOutputStream(new FileOutputStream(newFile));
		success = client.retrieveFile(path + "/" + fileName, outputStream);
		outputStream.close();
		
		
		
		

		success = true;
		outputStream.close();

		if (success) {
			msg.append(fileName + " has been downloaded successfully.");
			return msg;
		}

		else if (!success) {
			msg.append(" Ftp-Error        : " + fileName + " NOT FOUND ON SARA6!!");
			newFile.delete();

		} else /* if (!client.isConnected()) */ {
			msg.append(" \nFtp-Error        : " + fileName + " download failed.\n");
		} /*
			 * else msg.append(" Ftp-Error        :ERROR!!!\n");
			 */

		return msg;

	}

	
}
