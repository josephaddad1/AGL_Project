package com.capitalbanker.cbk.delivery.shared.utils;

import java.util.Arrays;
import java.util.List;

public class StringUtils {

	
	public static List<String> constructListFromCommaSeparatedElement(String elementStr)
	{
		if( elementStr ==null ||elementStr.isEmpty())return null;
		return Arrays.asList(elementStr.split("\\s*,\\s*"));
	}
}
