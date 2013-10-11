package de.seco.serp;

import org.neo4j.graphdb.GraphDatabaseService;

public class DataSource {

	private static GraphDatabaseService graphDb;
	
	public static GraphDatabaseService getGraphDB () {
		return graphDb;
	}
}
