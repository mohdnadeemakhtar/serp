package de.seco.serp.services;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import de.seco.serp.DataSource;
import de.seco.serp.util.SerpDbNodeDefinition;
import de.seco.serp.util.SerpDbPropertyDefintion;
import de.seco.serp.util.SerpDbRelationshipDefinition;
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
	
	
	private boolean _setRelationshipProperties (Relationship relationship, HashMap<String, String> properties) {
		// TODO: validation
		for (Map.Entry<String, String> prop : properties.entrySet()) {
			relationship.setProperty (prop.getKey(), prop.getValue());
		}
		return true;
	}
	
	public Relationship createRelationship (Long node1Id, Long node2Id, String type, HashMap<String, String> properties) {
		
		// validate against db schema definiton
		SerpDbSchemaDefinition dbDef = SerpDbSchemaDefinition.getInstance();
		SerpDbRelationshipDefinition relDef = dbDef.getRelationshipType(type);
		
		if (relDef == null) {
			System.out.println("illegal relation type");
			return null;
		}
		
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		Transaction tx = graphDb.beginTx();
		
		try {
			
			Node node1, node2;
			try {
				node1 = graphDb.getNodeById(node1Id);
			}
			catch (Exception e) {
				System.out.println("cannot find first node " + node1Id);
				return null;
			}
			try {
				node2 = graphDb.getNodeById(node2Id);
			}
			catch (Exception e) {
				System.out.println("cannot find second node " + node2Id);
				return null;
			}
			
			Relationship relationship = node1.createRelationshipTo(node2, DynamicRelationshipType.withName(type));
			if (!_setRelationshipProperties (relationship, properties)) {
				return null;
			}
			
			tx.success();
			return relationship;
			
		}
		catch (Exception e) {
			System.out.println("could not create relationship: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		finally {
			tx.finish();
		}
		
	}
	
}
