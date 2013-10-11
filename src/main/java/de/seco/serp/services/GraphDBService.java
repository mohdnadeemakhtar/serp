package de.seco.serp.services;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import de.seco.serp.DataSource;
import de.seco.serp.util.SerpDbNodeDefinition;
import de.seco.serp.util.SerpDbPropertyDefintion;
import de.seco.serp.util.SerpDbSchemaDefinition;

public class GraphDBService extends BaseService {

	public Node createNode (String type, HashMap<String, String> properties) {
		
		// validate against db schema definiton
		SerpDbSchemaDefinition dbDef = SerpDbSchemaDefinition.getInstance();
		
		SerpDbNodeDefinition nodeDef = dbDef.getNodeType(type);
		
		System.out.println("check type " +type + ": " +nodeDef);
		
		if (nodeDef == null ) {
			System.out.println("illegal node type");
			return null;
		}
		
		if (!nodeDef.validateProperties(properties)) {
			System.out.println("illegal node properties");
			return null;
		}
		
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		Transaction tx = graphDb.beginTx();
		Node node;
		
		try {
			node = graphDb.createNode();
			
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
