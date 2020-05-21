package com.capitalbanker.cgb.buisnessobjects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.capitalbanker.cgb.constants.ProjectConstants;
import com.capitalbanker.cgb.utils.Utils;
import com.sun.management.ThreadMXBean;

public abstract class  FileBO {

	protected String path;
	protected String name;
	protected String version;
	protected List<String> lineList;
	protected String linesString;
	protected List<String> errors;
	protected List<String> success;
	protected List<String> warnings;
	
	protected StringBuilder fileBOErrors;
	protected StringBuilder fileBOWarnings;
	

	// constructors

	public FileBO(String path, String name) throws IOException, ClassNotFoundException {
		this.name = name;
		this.path = path;
		lineList = new ArrayList<String>();
		errors = new ArrayList<>();
		success = new ArrayList<>();
		this.read();
		warnings = new ArrayList<>();
		linesString = Utils.ListToString(lineList);
		

		fileBOErrors= new StringBuilder("");
		fileBOWarnings= new StringBuilder("");

	}
	
	public FileBO(String name) {
		this.name = name;
		
		errors = new ArrayList<>();
		success = new ArrayList<>();
		warnings = new ArrayList<>();
		
		fileBOErrors= new StringBuilder("");
		fileBOWarnings= new StringBuilder("");

	}
	
	public FileBO() {
		errors = new ArrayList<>();
		warnings = new ArrayList<>();
		fileBOErrors= new StringBuilder("");
		fileBOWarnings= new StringBuilder("");
	}

	public abstract String getType();

	
	// child methods
	public boolean isPackage() {
		return false;
	}
	
	
	public boolean checkBody() {
		return false;
	}

	// ddl
	public boolean isPackageSpec() {
		return true;
	}

	public boolean isPackageBody() {
		return true;
	}

	public boolean hasWarning() {
		return this.warnings != null && this.warnings.size() > 0;
	}

	public void control() throws SQLException, IOException, InterruptedException, ClassNotFoundException {

	}

	
	public boolean isRegularExpressionFound(String regularExpression, String stringToTest) {
		String temp = new String();

		Pattern pattern = Pattern.compile(regularExpression, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(stringToTest);

		while (matcher.find()) {
			temp = matcher.group();
		}

		if (temp.isEmpty()) {
			return false;
		}

		return true;
	}

	protected boolean checkHeader() {

		return isRegularExpressionFound(ProjectConstants.name_reg_expression + name, linesString)
				&& isRegularExpressionFound(ProjectConstants.date_reg_expression, linesString);

	}
	
	protected boolean checkVersion() {
	return isRegularExpressionFound(ProjectConstants.version_reg_expression, linesString);
	}

	public boolean checkFooter() {
		boolean end = false;

		Pattern pattern = Pattern.compile("fin " + name, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(linesString);

		while (matcher.find()) {
			end = true;
		}

		return end;

	}

	public boolean isParent() {
		return false;
	}

	private void read() throws IOException {

		BufferedReader br = null;
		InputStream fis = null;
		try {

			File newFile = new File(path + File.separator + name);
			Utils.printProgress(newFile+"\n");
			// Control for file existing in appl and the real directory
			if (newFile.exists()) {
				fis = new FileInputStream(newFile);

				InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

				br = new BufferedReader(isr);

				// works for java 8
				// br.lines().forEach(line -> lineList.add(line));

				while (br.ready()) {
					lineList.add(br.readLine());
				}
			} else
				lineList = null;

		} finally {
			if (br != null) {
				fis.close();
				br.close();
			}
		}
	}

	public long getCpuTime() {
		ThreadMXBean bean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
		return bean.isCurrentThreadCpuTimeSupported() ? bean.getCurrentThreadCpuTime() : 0L;
	}

	
	public String getErrorString() {
		int i = 0;
		
		for (String error : errors) {
			if (i == 0)
				fileBOErrors.append("\n"+name);
			i=1;
			fileBOErrors.append("\n" + error);
		}
		
		fileBOErrors.append("\n");
		
		return fileBOErrors.toString();
	}
	
	public String getWarningsString() {
		int i = 0;
		
		for (String warning : warnings) {
			if (i == 0)
				fileBOWarnings.append("\n"+name);
			i=1;
			fileBOWarnings.append("\n" + warning);
		}

		fileBOWarnings.append("\n");
		return fileBOWarnings.toString();
	}



	// Getters & setters

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setsuccess(List<String> success) {
		this.success = success;
	}

	public List<String> getsuccess() {
		return success;
	}

	public List<String> getLineList() {
		return lineList;
	}

	public void setLineList(List<String> lineList) {
		this.lineList = lineList;
	}

	public String getLinesString() {
		return linesString;
	}

	public void setLinesString(String linesString) {
		this.linesString = linesString;
	}

	public boolean hasErrors() {
		return this.errors != null && this.errors.size() > 0;
	}



	public List<String> getSuccess() {
		return success;
	}

	public void setSuccess(List<String> success) {
		this.success = success;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	public StringBuilder getFileBOErrors() {
		return fileBOErrors;
	}

	public void setFileBOErrors(StringBuilder fileBOErrors) {
		this.fileBOErrors = fileBOErrors;
	}

	public StringBuilder getFileBOWarnings() {
		return fileBOWarnings;
	}

	public void setFileBOWarnings(StringBuilder fileBOWarnings) {
		this.fileBOWarnings = fileBOWarnings;
	}

	
	
	

}
