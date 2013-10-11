package de.seco.serp.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

public class SerpDbRelationshipDefinition {
	HashMap<String, SerpDbPropertyDefintion> properties;
	String displayLabel;
	String node1;
	String node2;
	String directed;
	
	public SerpDbRelationshipDefinition(JsonNode node) {
		this.properties = new HashMap<String, SerpDbPropertyDefintion>();
		this.displayLabel = null;
		
		addDisplayLabel(node.get("display_label"));
		
		addPropertyDefinitions(node.get("properties"));
		
		JsonNode directedNode = node.get("directed");
		JsonNode node1Node = node.get("node1");
		JsonNode node2Node = node.get("node2");
		
		if(node1Node != null){
			this.node1 = node1Node.getTextValue();
		}
		if(node2Node != null){
			this.node2 = node2Node.getTextValue();
		}
		if(directedNode != null){
			this.directed = directedNode.getTextValue();
		}
		
	}
	
	private Boolean addPropertyDefinitions(JsonNode propertiesRootNode){
		Iterator<Map.Entry<String, JsonNode>> nodeIterator = propertiesRootNode.getFields();
		while (nodeIterator.hasNext()){
			Map.Entry<String, JsonNode> propertyWithKey = nodeIterator.next();
			String key = propertyWithKey.getKey();
			SerpDbPropertyDefintion propertyDefinition = new SerpDbPropertyDefintion(propertyWithKey.getValue());
			addPropertyToRelationship(key, propertyDefinition);
		}
		return true;
	}
	
	private Boolean addDisplayLabel(JsonNode displayLabelNode){
		if(displayLabelNode != null){
			this.displayLabel = displayLabelNode.getTextValue();
		}
		return true;
	}
	
	private Boolean addPropertyToRelationship(final String key, final SerpDbPropertyDefintion propertyDefinition){
		Object obj = this.properties.get(key);
		if (obj != null){
			System.out.println("Property already exists");
			return false;
		}
		this.properties.put(key, propertyDefinition);
		return true;
	}
	
	public String toString(){
		return "\nrelationship: "+this.node1+"--"+this.displayLabel+"--"+this.node2+"\nproperties:"+this.properties+"\n";	
	}
}
