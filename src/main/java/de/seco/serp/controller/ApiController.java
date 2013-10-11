package de.seco.serp.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiController extends BaseController {
	
	public void testMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "Max Mustermann");
		map.put("age", "123");
		render(map, "json");
	}
	
	public void testMethod2(HttpServletRequest request, HttpServletResponse response) {
		render("<html><body><h1>Test</h1></body></html>");
	}

}
