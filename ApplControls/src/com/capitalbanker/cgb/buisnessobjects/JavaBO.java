package com.capitalbanker.cgb.buisnessobjects;

import java.io.IOException;

public class JavaBO extends FileBO{
	


	public JavaBO(String name, String path) throws ClassNotFoundException, IOException
	{
		 super(name,path);
		

	}
	
	public JavaBO(String name) {
		super(name);
	}
	
	public JavaBO() {
		
	}
	@Override
	public String  getType() {
		return "java";
	}
	@Override
	public void control() {
		// TODO Auto-generated method stub
		
	}


}
