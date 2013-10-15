package de.seco.serp.services;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import de.seco.serp.DataSource;
import de.seco.serp.util.SerpDbNode;
import de.seco.serp.util.SerpDbNodeDefinition;
import de.seco.serp.util.SerpDbPropertyDefinition;
import de.seco.serp.util.SerpDbRelationship;
import de.seco.serp.util.SerpDbRelationshipDefinition;
import de.seco.serp.util.SerpDbSchemaDefinition;

public class GraphDBService extends BaseService {

	public Node createNode (String type, HashMap<String, String> properties) {

		try{
			SerpDbNode serpNode = new SerpDbNode(type, properties);
			
			Node node = serpNode.create();
			
	
			return node;
		} catch (Exception e){
			return null;
		}
	}
	
	
	public Relationship createRelationship (long node1Id, long node2Id, String type, HashMap<String, String> properties) {
		
		try {
		
			SerpDbRelationship serpRelation = new SerpDbRelationship(node1Id, node2Id, type, properties);
			
			Relationship relationship = serpRelation.create();
	
			return relationship;
			
		} catch (Exception e){
			return null;
		}
	}
	
}
