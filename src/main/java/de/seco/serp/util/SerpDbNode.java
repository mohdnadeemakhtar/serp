package de.seco.serp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;


import de.seco.serp.DataSource;
import de.seco.serp.exception.InvalidNodeDefinitionException;

public class SerpDbNode {
	private HashMap<String, Object> properties;
	private String nodeType;
	private SerpDbNodeDefinition definition;
	private Long id;
	
	public SerpDbNode(String nodeType, HashMap<String, String> properties){
		SerpDbSchemaDefinition schemaDefinition = SerpDbSchemaDefinition.getInstance();
		this.definition = schemaDefinition.getNodeType(nodeType);
		if(this.definition == null){
			System.out.println("No valid node type");
			
			return;
		}

		if(!this.definition.validateProperties(properties)){
			System.out.println("Property validation failed");
			return;
		}
		this.nodeType = nodeType;
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
		
		
	}
	
	
	public SerpDbNode(Long id, HashMap<String, String> properties){
		GraphDatabaseService graphDb = DataSource.getGraphDb();

		
		Node node = graphDb.getNodeById(id);

		SerpDbSchemaDefinition schemaDefinition = SerpDbSchemaDefinition.getInstance();
		this.nodeType = node.getLabels().iterator().next().name();
		this.definition = schemaDefinition.getNodeType(this.nodeType);
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
		
		
	}
	
	public SerpDbNode(Node node) throws InvalidNodeDefinitionException {
		ResourceIterator<Label> iterator = node.getLabels().iterator();
		if (iterator.hasNext()){
			this.nodeType = iterator.next().name();
		} else {
			this.nodeType = "";
		}
		this.properties = new HashMap<String, Object>();
		SerpDbSchemaDefinition schemaDefinition = SerpDbSchemaDefinition.getInstance();
		this.definition = schemaDefinition.getNodeType(this.nodeType);
		
		for(Map.Entry<String, SerpDbPropertyDefinition> property: this.definition.getProperties().entrySet()){
			String propertyName = property.getKey();
			if(node.hasProperty(propertyName)){

				this.properties.put(propertyName, node.getProperty(propertyName));
				
			}
		}
		this.id = node.getId();
	}

	public Long getId(){
		return this.id;
	}
	
	public HashMap<String, Object> getProperties(){
		return this.properties;
	}
	
	public String getNodeType(){
		return this.nodeType;
	}
	
	private void setPropertyDefaultValue(String propertyName) {
		SerpDbPropertyDefinition propertyDefinition = this.definition.getPropertyDefinition(propertyName);
		this.properties.put(propertyName, propertyDefinition.getDefaultValue());
		
	}

	public Object getPropertyWithName(String name){
		if(properties.containsKey(name)){
			return properties.get(name);
		}
		System.out.println("property not found");
		return null;
	}
	
	public boolean setProperty(String key, String value){
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

	public boolean save() {
		GraphDatabaseService graphDb = DataSource.getGraphDb();

		Node node;
		
		
		if (this.id != null){
			node = graphDb.getNodeById(this.id);
		} else {
			node = graphDb.createNode();
			node.addLabel(DynamicLabel.label(this.nodeType));
			this.id = node.getId();
		}

		for (Map.Entry<String, Object> prop : properties.entrySet()) {
			node.setProperty (prop.getKey(), prop.getValue());
		}
	
		return true;
	}
	
	public boolean delete() {
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		Node node;
		if(this.id != null){
			node = graphDb.getNodeById(this.id);
			
//				delete all relationships of node to delete
			for(Relationship rel : node.getRelationships()){
				rel.delete();
			}
			node.delete();
		}
		return true;
	}
	
	public static SerpDbNode getById(Long nodeId){
		GraphDatabaseService graphDb = DataSource.getGraphDb();
		
		Node node = graphDb.getNodeById(nodeId);
		SerpDbNode serpNode = null;
		try {
			serpNode = new SerpDbNode(node);
		} catch (InvalidNodeDefinitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return serpNode;
	}
	
}
