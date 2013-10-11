package de.seco.serp;

import javax.servlet.ServletContext; 
import javax.servlet.ServletContextEvent; 
import javax.servlet.ServletContextListener;

import de.seco.serp.util.SerpDbSchemaDefinition;
	
public class MainServletContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println ("SERP is starting...");
		DataSource.initGraphDb();
		if (!SerpDbSchemaDefinition.getInstance().initSchema()) {
			System.out.println("could not init schema");
			System.exit(1);
		}
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println ("SERP is stopped.");
		DataSource.closeGraphDb();
	}
}
