package de.seco.serp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;

import de.seco.serp.DataSource;
import de.seco.serp.exception.InvalidNodeDefinitionException;
import de.seco.serp.exception.InvalidRelationshipDefinitionException;

public class SerpDbRelationship {
	private HashMap<String, Object> properties;
	private String relationshipType;
	private Long startNodeId;
	private Long endNodeId;
	private SerpDbRelationshipDefinition definition;
	private Long id;
	
	public SerpDbRelationship(long startNodeId, long endNodeId, String relationshipType, HashMap<String, String> properties){
		SerpDbSchemaDefinition schemaDefinition = SerpDbSchemaDefinition.getInstance();
		this.definition = schemaDefinition.getRelationshipType(relationshipType);
		if(this.definition == null){
			System.out.println("No valid node type");
			
			return;
		}

		if(!this.definition.validateProperties(properties)){
			System.out.println("Property validation failed");
			return;
		}
		this.relationshipType = relationshipType;
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
	
	public SerpDbRelationship(Long relationshipId, HashMap<String, String> properties) {
		SerpDbSchemaDefinition schemaDefinition = SerpDbSchemaDefinition.getInstance();
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		Relationship relationship = graphDb.getRelationshipById(relationshipId);
		
		this.relationshipType = relationship.getType().name();
		
		this.definition = schemaDefinition.getRelationshipType(relationshipType);
		if(this.definition == null){
			System.out.println("No valid node type");
			
			return;
		}

		if(!this.definition.validateProperties(properties)){
			System.out.println("Property validation failed");
			return;
		}
		
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
		
		
		this.startNodeId = relationship.getStartNode().getId();
		this.endNodeId = relationship.getEndNode().getId();
	}

	public SerpDbRelationship(Relationship relationship) throws InvalidRelationshipDefinitionException {
		try {
			this.relationshipType = relationship.getType().name();
			
			this.properties = new HashMap<String, Object>();
			SerpDbSchemaDefinition schemaDefinition = SerpDbSchemaDefinition.getInstance();
			this.definition = schemaDefinition.getRelationshipType(this.relationshipType);
			
			for(Map.Entry<String, SerpDbPropertyDefinition> property: this.definition.getProperties().entrySet()){
				String propertyName = property.getKey();
				if(relationship.hasProperty(propertyName)){
	
					this.properties.put(propertyName, relationship.getProperty(propertyName));
					
				}
			}
			this.id = relationship.getId();
			this.startNodeId = relationship.getStartNode().getId();
			this.endNodeId = relationship.getEndNode().getId();
		} catch(Exception e){
			throw new InvalidRelationshipDefinitionException();
		}
	}

	public String getRelationshipType(){
		return this.relationshipType;
	}
	
	public HashMap<String, Object> getProperties(){
		return this.properties;
	}
	
	public Long getId(){
		return this.id;
	}
	
	public Long getStartNodeId(){
		return this.startNodeId;
	}
	
	public Long getEndNodeId(){
		return this.endNodeId;
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
	
	public boolean save(){
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		

		Node node1, node2;
		try {
			node1 = graphDb.getNodeById(this.startNodeId);
		}
		catch (Exception e) {
			System.out.println("cannot find first node " + this.startNodeId);
			return false;
		}
		
		try {
			node2 = graphDb.getNodeById(this.endNodeId);
		}
		catch (Exception e) {
			System.out.println("cannot find second node " + this.endNodeId);
			return false;
		}

		try {
			Relationship relationship;
			if(this.id != null){
				relationship = graphDb.getRelationshipById(this.id);
			} else {
				relationship = node1.createRelationshipTo(node2, DynamicRelationshipType.withName(this.relationshipType));
			}
			for (Map.Entry<String, Object> prop : properties.entrySet()) {
				relationship.setProperty (prop.getKey(), prop.getValue());
			}
		} catch(Exception e){
			System.out.println("failed to create relationship");
			return false;
		}
		
		
		return true;
	}

	public static SerpDbRelationship getById(Long relationshipId) {
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		
		Relationship relationship = graphDb.getRelationshipById(relationshipId);
		SerpDbRelationship serpRelationship = null;
		try {
			serpRelationship = new SerpDbRelationship(relationship);
		} catch (InvalidRelationshipDefinitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return serpRelationship;
	}

	public boolean delete() {
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		Relationship relationship;
		
		try{
			if(this.id != null){
				relationship = graphDb.getRelationshipById(this.id);
				
	
				relationship.delete();
			}
		} catch(Exception e){
			return false;
		}
		return true;
	}
}
