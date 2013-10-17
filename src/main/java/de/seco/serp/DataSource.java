package de.seco.serp;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class DataSource {

	private static GraphDatabaseService graphDb;

//	public final static String GRAPH_DB_PATH = "/SERP-graphdb";
	public final static String GRAPH_DB_PATH = "/Users/matthias/SERP-graphdb";
//	public final static String GRAPH_DB_PATH = "/Users/matthias/neo4j-community-2.0.0-M05/data/graph.db";
	
	public static void initGraphDb () {
		System.out.println("init graph database " + GRAPH_DB_PATH);
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( GRAPH_DB_PATH );
		
		registerShutdownHook( graphDb );
	}
	
	private static void registerShutdownHook(final GraphDatabaseService graphDb2) {
		
		 Runtime.getRuntime().addShutdownHook( new Thread()
		    {
		        @Override
		        public void run()
		        {
		        	System.out.println("shutting down graph database...");
		        	graphDb2.shutdown();
		        }
		    } );
		
	}

	public static void closeGraphDb () {
		System.out.println("close graph database");
		graphDb.shutdown();
	}
	
	public static GraphDatabaseService getGraphDb () {
		return graphDb;
	}
	
	
}
