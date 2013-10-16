package de.seco.serp;

import java.io.File;

import javax.servlet.ServletContext; 
import javax.servlet.ServletContextEvent; 
import javax.servlet.ServletContextListener;

import de.seco.serp.util.SerpDbSchemaDefinition;
	
public class MainServletContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println ("SERP is starting...");
		
		String possibleGraphDbPath = (new File(sce.getServletContext().getRealPath("/"))).toPath().getRoot().toString() + "SERP-graphdb";
		
		System.out.println ("possibleGraphDbPath: " + possibleGraphDbPath);
		//.getRealPath("/");
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
