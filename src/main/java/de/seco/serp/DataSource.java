package de.seco.serp;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class DataSource {

	private static GraphDatabaseService graphDb;
	public final static String GRAPH_DB_PATH = "/Users/matthias/SERP-graphdb";
	
	public static void initGraphDb () {
		System.out.println("init graph database " + GRAPH_DB_PATH);
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( GRAPH_DB_PATH );
		//registerShutdownHook( graphDb );
	}
	
	public static void closeGraphDb () {
		System.out.println("close graph database");
		graphDb.shutdown();
	}
	
	public static GraphDatabaseService getGraphDb () {
		return graphDb;
	}
}
