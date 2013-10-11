package de.seco.serp.controller;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiController extends BaseController {
	
	public void testMethod() {
		
//		try {
//			TimeUnit.MILLISECONDS.sleep(3000);
//		}
//		catch (Exception e) {};
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "Max Mustermann");
		map.put("age", "123");
		render(map, "json");
	}
	
	public void testMethod2() {
		render("<html><body><h1>Test</h1></body></html>");
	}
	
	
	public void createNode() {
		String type = request.getParameter("type");
		render ("create node of type " +  type + "<br>params: " + request.getParameterMap());
	}

}
