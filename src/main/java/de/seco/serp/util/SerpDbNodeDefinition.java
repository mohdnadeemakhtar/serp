package de.seco.serp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

public class SerpDbNodeDefinition {
	HashMap<String, SerpDbPropertyDefinition> properties;
	String displayLabel;
	
	public SerpDbNodeDefinition(JsonNode node){
		this.properties = new HashMap<String, SerpDbPropertyDefinition>();
		this.displayLabel = null;
		
		addDisplayLabel(node.get("display_label"));
		
		addPropertyDefinitions(node.get("properties"));
		
	}
	
	
	public boolean validateProperties(HashMap<String,String> properties){
		
		Iterator<Map.Entry<String, String>> iterator = properties.entrySet().iterator();
		while ( iterator.hasNext() ){
			Map.Entry<String, String> keyAndValue = iterator.next();
			
			if(! this.validateProperty(keyAndValue.getKey(), keyAndValue.getValue())){
				return false;
			}
			
		}
		return true;
	}
	
	public boolean validateProperty(String key, String value){
		
		if(!this.properties.containsKey(key)){
			return false;
		}
		SerpDbPropertyDefinition propertyDefinition = this.properties.get(key);
		if(!propertyDefinition.validateValue(value)){
			return false;
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
			SerpDbPropertyDefinition propertyDefinition = new SerpDbPropertyDefinition(propertyWithKey.getValue());
			addPropertyToNode(key, propertyDefinition);
		}
		return true;
	}
	
	
	private boolean addPropertyToNode(final String key, final SerpDbPropertyDefinition propertyDefinition){
		Object obj = this.properties.get(key);
		if (obj != null){
			System.out.println("Property already exists");
			return false;
		}
		this.properties.put(key, propertyDefinition);
		return true;
	}
	
	public SerpDbPropertyDefinition getPropertyDefinition(String key){
		if(!this.properties.containsKey(key)){
			System.out.println("Property definition not found");
			return null;
		}
		
		return this.properties.get(key);
	}


	public ArrayList<String> getRequiredProperties() {
		ArrayList<String> requiredProperties = new ArrayList<String>();
		for (Map.Entry<String, SerpDbPropertyDefinition> property : this.properties.entrySet()) {
			String key = property.getKey();
			SerpDbPropertyDefinition value = property.getValue();
			if(value.isRequired()){
				requiredProperties.add(key);
			}
		}
		return null;
	}
	
	
//	public String toString(){
//		return "\nnode: displayLabel:"+displayLabel+"\nproperties:"+properties+"\n";
//	}

}
