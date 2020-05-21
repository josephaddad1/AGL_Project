package com.capitalbanker.cgb.builder;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capitalbanker.cgb.buisnessobjects.ApplBO;
import com.capitalbanker.cgb.buisnessobjects.DdlBO;
import com.capitalbanker.cgb.buisnessobjects.FileBO;
import com.capitalbanker.cgb.buisnessobjects.InsBO;
import com.capitalbanker.cgb.buisnessobjects.JavaBO;
import com.capitalbanker.cgb.connection.SFTPConnection;
import com.capitalbanker.cgb.constants.ProjectConstants;
import com.capitalbanker.cgb.utils.Utils;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;

public class ApplToJavaBuilder {
	private ApplBO applBO;
	private String tempPath;

	// first appl
	public ApplToJavaBuilder(String applPath, String applName)

			throws ClassNotFoundException, IOException {

		this.applBO = new ApplBO(applPath, applName);

	}

	public ApplToJavaBuilder(String applPath, String applName, Date releaseDate)

			throws ClassNotFoundException, IOException {

		this.applBO = new ApplBO(applPath, applName);
		this.applBO.setReleaseDate(releaseDate);

	}

	public ApplBO build(boolean recursive)
			throws ClassNotFoundException, IOException, SQLException, JSchException, ParseException {

		Utils.printProgress("Setting Source for " + applBO.getName() + ".....");
		applBO.setSource(getHeaderElement(ProjectConstants.source_reg_expression, applBO.getLinesString()));
		Utils.printProgress("DONE!\n");

		applBO.setDate(getHeaderElement(ProjectConstants.date_reg_expression, applBO.getLinesString()));

//		tempPath = Utils.getPropertiesFile().getProperty("TEMP_PATH") + ProjectConstants.FILE_SEPERATOR
//				+ applBO.getName() + "_" + ProjectConstants.runId;
		
		tempPath = Utils.getPropertiesFile().getProperty("TEMP_PATH");

		fillElementListInApplBO(recursive);

		return applBO;
	}

	public void fillElementListInApplBO(boolean recursive)
			throws IOException, ClassNotFoundException, SQLException, JSchException, ParseException {

		List<String> list = new ArrayList<String>();

		if (applBO == null) {
			return;
		}

		for (String elementType : ProjectConstants.reg_expression) {

			list = getElementList(elementType);

			if (recursive) {
				if (applBO.isCapbTst()) {
					FTPdownloadFiles(list, tempPath, Utils.getPropertiesFile().getProperty("ftp_capb_path"));
				}
				if (applBO.isTst()) {
					FTPdownloadFiles(list, tempPath, Utils.getPropertiesFile().getProperty("ftp_tst_path"));
				}
				if (applBO.isBNQ()) {
					FTPdownloadFiles(list, tempPath, Utils.getPropertiesFile().getProperty("ftp_bnq_path"));
				}
			}
			if (!list.isEmpty()) {

				for (String name : list) {
					FileBO fileBO = null;

					if (Utils.getType(name).equals("ddl")) {
						fileBO = new DdlBO(tempPath + ProjectConstants.FILE_SEPERATOR + "ddl", name);
					}

					else if (Utils.getType(name).equals("ins")) {
						fileBO = new InsBO(tempPath + ProjectConstants.FILE_SEPERATOR + "ins", name);
					}

					else if (Utils.getType(name).equals("java")) {
						fileBO = new JavaBO(name);
					}


					if (name.endsWith("appl") && recursive) {

						ApplToJavaBuilder conv = new ApplToJavaBuilder(
								tempPath + ProjectConstants.FILE_SEPERATOR + "appl", name);
						fileBO = conv.build(recursive);

					}

					if (fileBO != null) {
		
							fileBO.setVersion(
									getHeaderElement(ProjectConstants.version_reg_expression, fileBO.getLinesString()));

						if (fileBO instanceof ApplBO) {

							applBO.getRecursiveAppl().add((ApplBO) fileBO);
						} else {

							applBO.addFileBO(fileBO);
						}
					}

				}

			}

			list.clear();
		}

	}

	public void FTPdownloadFiles(List<String> list, String toPath, String fromPath) throws IOException, JSchException {

		ChannelSftp client = SFTPConnection.getClient();

		File destFile = new File(toPath);
		int j = list.size();
		if (list != null) {
			for (int i = 0; i < j; i++) {

				Utils.printProgress("Downloading: " + list.get(i) + "......");
				String msg = SFTPConnection.downloadFile(client, fromPath, list.get(i), destFile).toString();
				if (msg.contains("Error")) {
					Utils.printProgress("Failed!\n");
					applBO.getErrors().add(msg);
//				

				} else {
					applBO.getsuccess().add(msg);
					Utils.printProgress("DONE!\n");

				}
			}

		}
		SFTPConnection.disconnect();
	}

	// public ApplBO buildToCollect() {}

	private List<String> getElementList(String regularExpression) throws IOException {
		List<String> temp = new ArrayList<String>();
		Pattern pattern = Pattern.compile(regularExpression, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(applBO.getLinesString());

		String s;
		while (matcher.find()) {
			s = matcher.group();
			s = s.replaceAll("ins/", "");
			s = s.replaceAll("appl/", "");
			s = s.replaceAll("fmb60/", "");
			s = s.replaceAll("rpt60/", "");
			s = s.replaceAll("sql/", "");
			s = s.replaceAll("sqr/", "");
			s = s.replaceAll("sh/", "");
			s = s.replaceAll("dmp/", "");
			s = s.replaceAll("ctl/", "");
			s = s.replaceAll("ddl/", "");
			s = s.replaceAll("doc/", "");

			temp.add(s);
		}

		return temp;
	}

	/**
	 * @author joseph.al haddad
	 * 
	 * @return name or date or source of an appl file
	 */
	private String getHeaderElement(String regularExpression, String applLinesStr) {
		String temp = new String();
		String temp2 = new String();
		Pattern pattern = Pattern.compile(regularExpression, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		if (applLinesStr == null) {
			return "";
		}
		Matcher matcher = pattern.matcher(applLinesStr);
		while (matcher.find()) {
			temp = matcher.group();
		}

		if (regularExpression.equals(ProjectConstants.name_reg_expression)) {
			pattern = Pattern.compile(ProjectConstants.appl_name_tag, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			matcher = pattern.matcher(temp);
		} else if (regularExpression.equals(ProjectConstants.date_reg_expression)) {

			pattern = Pattern.compile(ProjectConstants.appl_date_tag, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			matcher = pattern.matcher(temp);
		} else if (regularExpression.equals(ProjectConstants.source_reg_expression)) {
			pattern = Pattern.compile(ProjectConstants.appl_source_tag, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			matcher = pattern.matcher(temp);

		} else if (regularExpression.equals(ProjectConstants.version_reg_expression)) {
			pattern = Pattern.compile(ProjectConstants.version_tag, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			matcher = pattern.matcher(temp);

		}

		while (matcher.find()) {
			temp2 = matcher.group();
		}
		temp = temp.replace(temp2, "");

		return temp.replaceAll("\\s", "");
	}

}
