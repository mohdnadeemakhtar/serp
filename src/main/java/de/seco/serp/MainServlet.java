package de.seco.serp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

	protected void route(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println ("route....");

		response.setContentType("text/html;charset=UTF-8");
		 PrintWriter out = response.getWriter();
		 
		 out.write("SERP7 Test: " + request.getRequestURL() );
		 
		 out.close(); 
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    route(request, response);
	}
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    route(request, response);
	}
	    
}
