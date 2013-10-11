package de.seco.serp;

import javax.servlet.ServletContext; 
import javax.servlet.ServletContextEvent; 
import javax.servlet.ServletContextListener;
	
public class MainServletContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println ("SERP is starting...");
		
		DataSource.initGraphDB();
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println ("SERP is stopped.");
		DataSource.closeGraphDB();
	}
}
