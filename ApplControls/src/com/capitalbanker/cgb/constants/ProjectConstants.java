package com.capitalbanker.cgb.constants;

import java.util.Date;
import java.util.HashMap;
import java.util.NavigableMap;

public class ProjectConstants {
	
	
	//typeId in dataBase
	public static String applId = "";
	public static 	String ddlId = "";
	public static String insId = "";
	public static 	String javaId = "";
	public static 	String sqlId = "";
	public static String fmbId = "";
	public static String shId = "";
	public static String rptId = "";
	public static String unknownId = "";
	
	
	
	
	public static HashMap<String, String> moduleId= new HashMap<String, String>();
	
	public static NavigableMap<Date, NavigableMap<String, String>> versions= null;
	public static String runId="";
	
	
	// Path for test appl


	
	// Standard Tags of APPL
	public static final String appl_name_tag = "(\\s*\\<Nom\\s*\\>)";
	public static final String appl_source_tag = "(\\s*\\<Source\\s*\\>)";
	public static final String appl_date_tag = "(\\s*\\<Date\\s*\\>)";
	public static final String version_tag = "(\\s*\\<Version\\s*\\>)";
	

	// Regular Expressions
	public static final String appl_reg_expression = "^(( )*appl\\/?[a-z0-9_\\.-]+)";
	public static final String ddl_reg_expression = "^(( )*ddl\\/?[a-z0-9_\\.-]+)";
	public static final String fmb60_reg_expression = "^(( )*fmb60\\/?[a-z0-9_\\.-]+)";
	public static final String rpt60_reg_expression = "^(( )*rpt60\\/?[a-z0-9_\\.-]+)";
	public static final String sql_reg_expression = "^(( )*sql\\/?[a-z0-9_\\.-]+)";
	public static final String sqr_reg_expression = "^(( )*sqr\\/?[a-z0-9_\\.-]+)";
	public static final String ins_reg_expression = "^(( )*ins\\/?[a-z0-9_\\.-]+)";
	public static final String ctl_reg_expression = "^(( )*ctl\\/?[a-z0-9_\\.-]+)";
	public static final String sh_reg_expression = "^(( )*sh\\/?[a-z0-9_\\.-]+)";
	public static final String doc_reg_expression = "^(( )*doc\\/?[a-z0-9_\\.-]+)";
	public static final String dmp_reg_expression = "^(( )*dmp\\/?[a-z0-9_\\.-]+)";
	public static final String name_reg_expression = "(\\s*\\<Nom\\s*\\>)\\s*";
	public static final String date_reg_expression = "(\\s*\\<Date\\s*\\>)\\s*[a-z\\_\\-\\.0-9]+";
	public static final String java_reg_expression = "^(( )*[a-z0-9\\-]+\\.war)";
	
	
	public static final String[] reg_expression = {appl_reg_expression,ddl_reg_expression,fmb60_reg_expression,rpt60_reg_expression,
												sql_reg_expression,sqr_reg_expression,ins_reg_expression,ctl_reg_expression,sh_reg_expression,
												doc_reg_expression,dmp_reg_expression , java_reg_expression};


	public static final String version_reg_expression = "(\\s*\\<Version\\s*\\>)\\s*(([1-9]\\d*)\\.(\\d+)\\.(\\d+)\\.(\\d))\\s";

	public static final String description_reg_expression = "(\\s*\\<Description\\s*\\>)";

	public static final String source_reg_expression = "(\\s*\\<Source\\s*\\>)\\s*[a-z\\_\\-\\.0-9\\$]+";

	// DDL body expression //..
	public static final String ddl_reg_expression_echo = "set echo on";
	public static final String ddl_reg_expression_vtit = "btit off";
	public static final String ddl_reg_expression_termout_feedback = "set termout on feedback on";
	public static final String ddl_reg_expression_slash = "/";
	public static final String ddl_reg_expression_show_error = "show errors";
	public static final String ddl_reg_expression_footer = "exit";

	public static final String[] ddl_reg_exp_list1 = { ddl_reg_expression_termout_feedback, ddl_reg_expression_slash,
			ddl_reg_expression_echo, ddl_reg_expression_vtit, ddl_reg_expression_show_error ,ddl_reg_expression_footer};
	
	public static final String[] ins_reg_exp_list1 = { ddl_reg_expression_termout_feedback, ddl_reg_expression_slash,
			ddl_reg_expression_footer};


	// Appl Source directories
	public static final String APPL_SOURCE_TST = "$TST";
	public static final String APPL_SOURCE_CAPBTST = "$CAPB_TST";
	public static final String APPL_SOURCE_BNQ = "$BNQ";
	public static final String APPL_SOURCE_TSTE = "$TSTE";

	// Obsolete functions
	public static final String trad_func = "pk_trad.rch_text";
	public static final String concat_func = "wm_concat";

	public static final String FILE_SEPERATOR = System.getProperty("file.separator");
	
	// Footer check
	public static final String end_file = "fin( )*[a-z\\_0-9\\.]*";
	
	
	

}
