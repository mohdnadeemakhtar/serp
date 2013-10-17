package de.seco.serp.util;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

public class Templates {

	public static String TEMPLATE_PATH = null;
	
	public static String allTemplates() {
		Iterator it = FileUtils.iterateFiles(new File(TEMPLATE_PATH), null, true);
		
		StringBuilder result = new StringBuilder();
		
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
			
			System.out.println("template: " +  templateName);
			
			String code;
			try {
				code = new Scanner(file).useDelimiter("\\Z").next();
				result.append("<script id=\"" + templateName + "\" type=\"text/template\" > \n" + code + "\n</script>");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return result.toString();
	}
	
}
