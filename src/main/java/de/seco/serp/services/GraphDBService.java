package de.seco.serp.services;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import de.seco.serp.DataSource;

public class GraphDBService extends BaseService {

	public Node createNode (String type, HashMap<String, String> properties) {
		
		// validate against db schema definiton
//		if (SerpDbSchemaDefinition.getNodeDef("type") == null) {
//			return null;
//		}
//		SerpDbSchemaDefinition.getNodeDef("type").validateProperty(name, value)
//		SerpDbSchemaDefinition.getInstance().getNodeDef("type").validateProperty(name, value)
		
		GraphDatabaseService graphDB = DataSource.getGraphDB();
		Transaction tx = graphDB.beginTx();
		Node node;
		
		try {
			node = graphDB.createNode();
			
			for (Map.Entry<String, String> prop : properties.entrySet()) {
				node.setProperty (prop.getKey(), prop.getValue());
			}
			
			tx.success();
		}
		catch (Exception e) {
			System.out.println("cannot create node: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		finally {
			tx.finish();
		}

		return node;
	}
	
}
