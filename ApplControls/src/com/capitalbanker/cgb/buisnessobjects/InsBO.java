package com.capitalbanker.cgb.buisnessobjects;

import java.io.IOException;
import java.sql.SQLException;

import com.capitalbanker.cgb.constants.ProjectConstants;



public class InsBO extends FileBO {

	public InsBO(String name, String path) throws ClassNotFoundException, IOException {
		super(name, path);

	}

	public InsBO() {

	}
	
	@Override
	public String getType()
	{
		return "ins";
	}


	@Override
	public boolean checkBody() {
		
		if (this.linesString.toLowerCase().contains("set termout on feedback on define on")
				&& this.linesString.toLowerCase().contains("/")
				&& this.linesString.toLowerCase().contains("exit"))
			return true;
		else
			return false;

	}

	
	@Override
	public void control() throws SQLException, IOException, InterruptedException {

		if (!checkHeader()) {
			errors.add(" Format-Error     : header isn't standard");
		}
		if (!checkVersion()) {
			warnings.add(" Format-Error     : version isn't standard");
		}
		checkInsBody();
		

		if (!checkFooter()) {
			errors.add(" Format-Error     : footer isn't standard");
		}
		
	}

	
	public void checkInsBody() {

		String linesLowerCase = this.linesString.toLowerCase();
		int i = 0;
		for (String x : ProjectConstants.ins_reg_exp_list1) {

			if (!linesLowerCase.contains(x)) {
				if (i == 0) {
					errors.add(" Format-Error     : '" + x.toUpperCase() + "' argument is missing.");
					i++;
				}else
				errors.add("                  : '" + x.toUpperCase() + "' argument is missing.");
			}
		}

	}

	

}
