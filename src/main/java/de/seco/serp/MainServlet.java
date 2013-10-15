package de.seco.serp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.seco.serp.controller.*;

public class MainServlet extends HttpServlet {

	
	protected void render_404 (HttpServletRequest request, HttpServletResponse response) {
		System.out.println("render_404");
		try {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} 
		catch (Exception e) {
			System.out.println("cannot render 404: " + e.getMessage());
		}
	}
	
	// route request to controller
	protected void route(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestUri = request.getRequestURI();
		if (requestUri.startsWith("/")) {
			requestUri = requestUri.substring(1);
		}
		String[] uriParts = requestUri.split("/");
		

		BaseController controller = null;
		String actionName = "";
		HttpSession session = request.getSession(true);
		
		try {
			
			// Landing Page
			if (uriParts.length == 1) {
				actionName = uriParts[0];
				System.out.println("action: '"+actionName+"'");
				if (actionName.length() == 0) {
					if (session.getAttribute("userId") == null) {
						request.getRequestDispatcher("views/login.jsp").forward(request, response);
					}
					else {
						request.getRequestDispatcher("views/main.jsp").forward(request, response);
					}
					
					return;
				}
				
				controller = new AuthController();
			}
			
			else {
				actionName = uriParts[1];
				
				// api controller
				if (uriParts[0].equals("api")) {
					System.out.println("Api controller...");
					controller = new ApiController();
				}
				
				else {
					render_404(request, response);
					return;
				}
			}
			
			if (!controller.invoke(actionName, request, response)) {
				render_404(request, response);
			}
		}
		
		catch (Exception e) {
			System.out.println("cannot route: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    route(request, response);
	}
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    route(request, response);
	}
	    
}
