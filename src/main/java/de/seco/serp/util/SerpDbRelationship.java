package de.seco.serp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import de.seco.serp.DataSource;

public class SerpDbRelationship {
	HashMap<String, Object> properties;
	String label;
	long startNodeId;
	long endNodeId;
	SerpDbRelationshipDefinition definition;
	
	public SerpDbRelationship(long startNodeId, long endNodeId, String label, HashMap<String, String> properties){
		SerpDbSchemaDefinition schemaDefinition = SerpDbSchemaDefinition.getInstance();
		this.definition = schemaDefinition.getRelationshipType(label);
		if(this.definition == null){
			System.out.println("No valid node type");
			
			return;
		}

		if(!this.definition.validateProperties(properties)){
			System.out.println("Property validation failed");
			return;
		}
		this.label = label;
		this.properties = new HashMap<String, Object>();
		ArrayList<String> requiredProperties = this.definition.getRequiredProperties();
		
		for (Map.Entry<String, String> property : properties.entrySet()) {
			String key = property.getKey();
			String value = property.getValue();
			this.setProperty(key, value);
			if(requiredProperties.contains(key)){
				requiredProperties.remove(key);
			}
			
		}
		
//		set remaining required properties if they have a default value
		for(String propertyName : requiredProperties){
			this.setPropertyDefaultValue(propertyName);
		}
		
		
		this.startNodeId = startNodeId;
		this.endNodeId = endNodeId;
			
	}

	private void setPropertyDefaultValue(String propertyName) {
		SerpDbPropertyDefinition propertyDefinition = this.definition.getPropertyDefinition(propertyName);
		this.properties.put(propertyName, propertyDefinition.getDefaultValue());
		
	}

	private boolean setProperty(String key, String value) {
		if(!this.definition.validateProperty(key, value)){
			System.out.println("Property validation failed");
			return false;
		}
		
		SerpDbPropertyDefinition propertyDefinition = this.definition.getPropertyDefinition(key);
		Object propertyValue = new Object();
		Class type = propertyDefinition.getType();
		if(type.equals(String.class)){
			propertyValue = value;
		} else if(type.equals(Integer.class)){
			propertyValue = Integer.parseInt(value);
		} else if(type.equals(Boolean.class)){
			propertyValue = Boolean.parseBoolean(value);
		} 
		this.properties.put(key, propertyValue);
		return true;
	}
	
	public Relationship create(){
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		Transaction tx = graphDb.beginTx();
		
		try {
			
			Node node1, node2;
			try {
				node1 = graphDb.getNodeById(this.startNodeId);
			}
			catch (Exception e) {
				System.out.println("cannot find first node " + this.startNodeId);
				return null;
			}
			try {
				node2 = graphDb.getNodeById(this.endNodeId);
			}
			catch (Exception e) {
				System.out.println("cannot find second node " + this.endNodeId);
				return null;
			}
			
			Relationship relationship = node1.createRelationshipTo(node2, DynamicRelationshipType.withName(this.label));
			for (Map.Entry<String, Object> prop : properties.entrySet()) {
				relationship.setProperty (prop.getKey(), prop.getValue());
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
