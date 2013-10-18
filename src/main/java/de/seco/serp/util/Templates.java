package de.seco.serp.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;


public class Templates {

	public static String TEMPLATE_PATH = null;
	
	/*
	 * returns map of templates
	 */
	public static HashMap<String, String> allTemplates() {
		Iterator it = FileUtils.iterateFiles(new File(TEMPLATE_PATH), null, true);
		
		HashMap<String, String> map = new HashMap<String, String> ();
		
		while (it.hasNext()) {
			File file = (File) it.next();
			String fileName = file.getName();
			if (!fileName.startsWith("_")) {
				continue;
			}
			
			String templateName = fileName.substring(1);
			int extPos = templateName.lastIndexOf('.');
			if (extPos != -1) {
				templateName = templateName.substring(0, extPos);
			}
			
			String code;
			try {
				code = new Scanner(file).useDelimiter("\\Z").next();
				map.put(templateName, code);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		try {
			return map;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
