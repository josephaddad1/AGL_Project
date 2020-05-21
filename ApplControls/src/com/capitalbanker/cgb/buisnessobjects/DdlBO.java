package com.capitalbanker.cgb.buisnessobjects;

import java.io.IOException;
import java.sql.SQLException;
import com.capitalbanker.cgb.constants.ProjectConstants;
import com.capitalbanker.cgb.utils.Utils;

public class DdlBO extends FileBO {

	public DdlBO(String name, String path) throws ClassNotFoundException, IOException, SQLException {
		super(name, path);
		linesString = Utils.ListToString(lineList);

	}

	public DdlBO() {

	}

	@Override
	public String getType() {
		return "ddl";
	}

	@Override
	public void control() throws IOException, SQLException, InterruptedException {

		// if not found or empty
		if (lineList != null && !lineList.isEmpty()) {

			containRestrictedFunctions();

			if (!checkHeader()) {
				errors.add(" Format-Error     : header isn't standard");
			}

			if (!checkVersion()) {
				warnings.add(" Format-Error     : version isn't standard");
			}

			checkDDlBody();

			if (!checkFooter()) {
				errors.add(" Format-Error     : footer isn't standard");
			}

		}

	}

	public void checkDDlBody() {

		String linesLowerCase = this.linesString.toLowerCase();
		int i = 0;
		for (String x : ProjectConstants.ddl_reg_exp_list1) {

			if (!linesLowerCase.contains(x)) {
				if (i == 0) {
					errors.add(" Format-Error     : '" + x.toUpperCase() + "' argument is missing.");
					i++;
				}else
				errors.add("                  : '" + x.toUpperCase() + "' argument is missing.");
			}
		}

	}

	public boolean isPackageBody() {
		return this.name.startsWith("pkb");
	}

	public boolean isPackageSpec() {
		return this.name.startsWith("pks");
	}

	public boolean isPackage() {
		return isPackageBody() || isPackageSpec();
	}

	

	private void containRestrictedFunctions() {

		if (linesString.contains(ProjectConstants.trad_func)) {
			errors.add(" Format-Error     : " + name + " contain PK_TRAD function\n");

		}

		if (linesString.contains(ProjectConstants.concat_func)) {
			errors.add(" Format-Error     : " + name + " contain CONCAT function\n");

		}

	}

	@Override
	public boolean checkHeader() {

		return super.checkHeader()
				&& isRegularExpressionFound(ProjectConstants.description_reg_expression, linesString);
	}
}
// Check Status in DB
