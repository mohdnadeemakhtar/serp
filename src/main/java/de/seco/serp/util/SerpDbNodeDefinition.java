package de.seco.serp.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

public class SerpDbNodeDefinition {
	HashMap<String, SerpDbPropertyDefintion> properties;
	String displayLabel;
	
	public SerpDbNodeDefinition(JsonNode node){
		this.properties = new HashMap<String, SerpDbPropertyDefintion>();
		this.displayLabel = null;
		
		addDisplayLabel(node.get("display_label"));
		
		addPropertyDefinitions(node.get("properties"));
		
	}
	
	private Boolean addDisplayLabel(JsonNode displayLabelNode){
		if(displayLabelNode != null){
			this.displayLabel = displayLabelNode.getTextValue();
		}
		
		return true;
	}
	
	private Boolean addPropertyDefinitions(JsonNode propertiesRootNode){
		Iterator<Map.Entry<String, JsonNode>> nodeIterator = propertiesRootNode.getFields();
		while (nodeIterator.hasNext()){
			Map.Entry<String, JsonNode> propertyWithKey = nodeIterator.next();
			String key = propertyWithKey.getKey();
			SerpDbPropertyDefintion propertyDefinition = new SerpDbPropertyDefintion(propertyWithKey.getValue());
			addPropertyToNode(key, propertyDefinition);
		}
		return true;
	}
	
	
	private Boolean addPropertyToNode(final String key, final SerpDbPropertyDefintion propertyDefinition){
		Object obj = this.properties.get(key);
		if (obj != null){
			System.out.println("Property already exists");
			return false;
		}
		this.properties.put(key, propertyDefinition);
		return true;
	}
	

}
