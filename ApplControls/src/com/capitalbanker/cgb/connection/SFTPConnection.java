package com.capitalbanker.cgb.connection;

import static com.capitalbanker.cgb.constants.ProjectConstants.FILE_SEPERATOR;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import com.capitalbanker.cgb.constants.ProjectConstants;
import com.capitalbanker.cgb.utils.Utils;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPConnection {
	private static ChannelSftp sftp;
	private static OutputStream outputStream;
	private static boolean success;

	private static StringBuilder msg = new StringBuilder("");

	private SFTPConnection() throws IOException {

	}

	public static ChannelSftp getClient() throws SocketException, IOException, JSchException {
		if (sftp == null) {
			SFTPConnect();

		}
		return sftp;

	}
	
	

	private static void SFTPConnect() throws SocketException, IOException, JSchException {

		JSch jsch = new JSch();
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");

		Session jschSession = jsch.getSession(Utils.getPropertiesFile().getProperty("ftp_username"),
				Utils.getPropertiesFile().getProperty("ftp_server"));

		jschSession.setConfig(config);
		jschSession.setPassword(Utils.getPropertiesFile().getProperty("ftp_password"));
		jschSession.connect();
		sftp = (ChannelSftp) jschSession.openChannel("sftp");
		sftp.connect();

	}

	public static StringBuilder downloadFile(ChannelSftp client, String path, String fileName, File NewApplFolder)
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

		String ftpPath = path + "/" + fileName;

		try {
			client.get(ftpPath, path1);
			success = true;
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			success = false;
			msg.append(" Ftp-Error        : " + fileName + " NOT FOUND ON SARA6!!");
			return msg;
		}
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

	public static void disconnect() {
		sftp.disconnect();
		sftp=null;
	}
}
