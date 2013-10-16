package de.seco.serp.controller;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.ObjectMapper;

import de.seco.serp.util.SerpDbNode;

/*
 * Each controller must extend this
 */
public class BaseController {

	HttpServletRequest request;
	HttpServletResponse response;
	HttpSession session;
	
	// invoke controller action via reflection
	public boolean invoke (final String methodName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String className = this.getClass().getCanonicalName();
		Class c = Class.forName(className);
		//Method method = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
		
		Method method;
		
		try {
			method = c.getMethod(methodName);
		}
		catch (Exception e) {
			return false;
		}
		
		// check if public
		if (!Modifier.isPublic(method.getModifiers())) {
			return false;
		}
		
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		method.invoke(this);
		return true;
	}
	
	public boolean renderAsJSON (Object value) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        mapper.writeValue(out, value);
        System.out.println("write json...");
        out.close();
        return true;
	}
	
	public boolean renderAsHTML (Object value) throws Exception {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(value);
        out.close();
        return true;
	}
	
	
	public boolean render (Object value, String type) {
		System.out.println("render " + type);
		try {
			if (type.equals("json")) {
				return renderAsJSON(value);
			}
			else {
				return renderAsHTML(value);
			}
		}
		catch (Exception e) {
			System.out.println("cannot render: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean render (Object value) {
		return render(value, "html");
	}
	
}
