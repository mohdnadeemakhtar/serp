package de.seco.serp;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class DataSource {

	private static GraphDatabaseService graphDb;
	public final static String GRAPH_DB_PATH = "C:/SERP-graphdb";
	
	public static void initGraphDB () {
		System.out.println("init graph database " + GRAPH_DB_PATH);
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( GRAPH_DB_PATH );
		//registerShutdownHook( graphDb );
	}
	
	public static void closeGraphDB () {
		System.out.println("close graph database");
		graphDb.shutdown();
	}
	
	public static GraphDatabaseService getGraphDB () {
		return graphDb;
	}
}
