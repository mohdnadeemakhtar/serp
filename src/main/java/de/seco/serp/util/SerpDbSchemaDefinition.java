package de.seco.serp.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class SerpDbSchemaDefinition {
	
	private static final String PATH_TO_SCHEMA = "./config/schema.json";
	private HashMap<String, SerpDbNodeDefinition> nodes;
	private HashMap<String, SerpDbRelationshipDefinition> relationships;
	private boolean initialized;
	static SerpDbSchemaDefinition instance;
	
	private SerpDbSchemaDefinition(){
		this.nodes = new HashMap<String, SerpDbNodeDefinition>();
		this.relationships = new HashMap<String, SerpDbRelationshipDefinition>();
		this.initialized = false;
	}
	
	static public SerpDbSchemaDefinition getInstance(){
		if(instance == null){
			instance = new SerpDbSchemaDefinition();
		}
		return instance;
	}
	
	public SerpDbNodeDefinition getNodeType(String type){
		
		if(this.nodes.containsKey(type)){
			return this.nodes.get(type);
		}
		
		return null;
	}
	
	public SerpDbRelationshipDefinition validateRelationshipType(String type){
	
		if(this.relationships.containsKey(type)){
			return this.relationships.get(type);
		}
		
		return null;
	}
	
	
	public boolean initSchema(){
		
		if(this.initialized){
			System.out.println("Database schema already initialized.");
			return false;
		}
		File schemaFile = new File(PATH_TO_SCHEMA);
		
		ObjectMapper mapper = new ObjectMapper();

		try {
			JsonNode rootNode = mapper.readTree(schemaFile);
			
			JsonNode nodeRootNode =rootNode.path("node-types");
			addNodes(nodeRootNode);
			JsonNode relationshipRootNode = rootNode.path("relationship-types");
			addRelationships(relationshipRootNode);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.initialized = true;
		return true;
	}
	
	
	
	
	
	private Boolean addNodes(JsonNode rootNode){
		Iterator<Map.Entry<String, JsonNode>> nodeIterator = rootNode.getFields();
		while (nodeIterator.hasNext()){
			Map.Entry<String, JsonNode> nodeWithKey = nodeIterator.next();
			String key = nodeWithKey.getKey();
			SerpDbNodeDefinition nodeDefinition = new SerpDbNodeDefinition(nodeWithKey.getValue());
			addNodeToSchema(key, nodeDefinition);
		}
		return true;
	}
	
	private Boolean addRelationships(JsonNode rootNode){
		Iterator<Map.Entry<String, JsonNode>> nodeIterator = rootNode.getFields();
		while (nodeIterator.hasNext()){
			Map.Entry<String, JsonNode> nodeWithKey = nodeIterator.next();
			String key = nodeWithKey.getKey();
			SerpDbRelationshipDefinition relationshipDefinition = new SerpDbRelationshipDefinition(nodeWithKey.getValue());
			addRelationshipToSchema(key, relationshipDefinition);
		}
		return true;
	}

	private Boolean addNodeToSchema(final String key, final SerpDbNodeDefinition nodeDefinition){
		Object obj = this.nodes.get(key);
		if (obj != null){
			System.out.println("Node-type already exists");
			return false;
		}
		this.nodes.put(key, nodeDefinition);
		return true;
	}
	private Boolean addRelationshipToSchema(final String key, final SerpDbRelationshipDefinition relationshipDefinition){
		Object obj = this.relationships.get(key);
		if (obj != null){
			System.out.println("Node-type already exists");
			return false;
		}
		this.relationships.put(key, relationshipDefinition);
		return true;
	}
	
//	public String toString(){
//		return "\nnodes: "+this.nodes+"\nrelationships:"+this.relationships+"\n"+"initialized:"+this.initialized+"\n";	
//	}
	
}
