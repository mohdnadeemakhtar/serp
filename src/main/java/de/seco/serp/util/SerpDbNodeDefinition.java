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
	
	
	public boolean validateProperties(HashMap<String,String> properties){
		
		Iterator<Map.Entry<String, String>> iterator = properties.entrySet().iterator();
		while ( iterator.hasNext() ){
			Map.Entry<String, String> keyAndValue = iterator.next();
			
			if(!this.properties.containsKey(keyAndValue.getKey())){
				return false;
			}
			SerpDbPropertyDefintion propertyDefinition = this.properties.get(keyAndValue.getKey());
			if(!propertyDefinition.validateValue(keyAndValue.getValue())){
				return false;
			}
			
		}
		return true;
	}
	
	
	private boolean addDisplayLabel(JsonNode displayLabelNode){
		if(displayLabelNode != null){
			this.displayLabel = displayLabelNode.getTextValue();
		}
		
		return true;
	}
	
	private boolean addPropertyDefinitions(JsonNode propertiesRootNode){
		Iterator<Map.Entry<String, JsonNode>> nodeIterator = propertiesRootNode.getFields();
		while (nodeIterator.hasNext()){
			Map.Entry<String, JsonNode> propertyWithKey = nodeIterator.next();
			String key = propertyWithKey.getKey();
			SerpDbPropertyDefintion propertyDefinition = new SerpDbPropertyDefintion(propertyWithKey.getValue());
			addPropertyToNode(key, propertyDefinition);
		}
		return true;
	}
	
	
	private boolean addPropertyToNode(final String key, final SerpDbPropertyDefintion propertyDefinition){
		Object obj = this.properties.get(key);
		if (obj != null){
			System.out.println("Property already exists");
			return false;
		}
		this.properties.put(key, propertyDefinition);
		return true;
	}
	
	
	
//	public String toString(){
//		return "\nnode: displayLabel:"+displayLabel+"\nproperties:"+properties+"\n";
//	}

}
