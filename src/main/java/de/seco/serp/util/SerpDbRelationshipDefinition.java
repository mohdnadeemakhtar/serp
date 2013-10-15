package de.seco.serp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

public class SerpDbRelationshipDefinition {
	HashMap<String, SerpDbPropertyDefinition> properties;
	String displayLabel;

	
	public SerpDbRelationshipDefinition(JsonNode node) {
		this.properties = new HashMap<String, SerpDbPropertyDefinition>();
		this.displayLabel = null;
		
		addDisplayLabel(node.get("display_label"));
		
		addPropertyDefinitions(node.get("properties"));

	}
	
	public boolean validateProperties(HashMap<String,String> properties){
		
		ArrayList<String> requiredProperties = this.getRequiredProperties();
		Iterator<Map.Entry<String, String>> iterator = properties.entrySet().iterator();
		while ( iterator.hasNext() ){
			Map.Entry<String, String> keyAndValue = iterator.next();
			
			if(! this.validateProperty(keyAndValue.getKey(), keyAndValue.getValue())){
				return false;
			}
			if(requiredProperties.contains(keyAndValue.getKey())){
				requiredProperties.remove(keyAndValue.getKey());
			}
		}
		if(requiredProperties.size() > 0){
			ArrayList<String> propertiesWithDefaultValue = this.getPropertiesWithDefaultValue();
		
			for(String requiredProperty : requiredProperties){
				if(propertiesWithDefaultValue.contains(requiredProperty)){
					requiredProperties.remove(requiredProperty);
				}
			}
			if(requiredProperties.size() > 0 ){
				System.out.println("not all required properties set");
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
	
	private boolean addPropertyDefinitions(JsonNode propertiesRootNode){
		Iterator<Map.Entry<String, JsonNode>> nodeIterator = propertiesRootNode.getFields();
		while (nodeIterator.hasNext()){
			Map.Entry<String, JsonNode> propertyWithKey = nodeIterator.next();
			String key = propertyWithKey.getKey();
			SerpDbPropertyDefinition propertyDefinition = new SerpDbPropertyDefinition(propertyWithKey.getValue());
			addPropertyToRelationship(key, propertyDefinition);
		}
		return true;
	}
	
	private boolean addDisplayLabel(JsonNode displayLabelNode){
		if(displayLabelNode != null){
			this.displayLabel = displayLabelNode.getTextValue();
		}
		return true;
	}
	
	private boolean addPropertyToRelationship(final String key, final SerpDbPropertyDefinition propertyDefinition){
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
		return requiredProperties;
	}
	
	public ArrayList<String> getPropertiesWithDefaultValue() {
		ArrayList<String> propertiesWithDefaultValue = new ArrayList<String>();
		for (Map.Entry<String, SerpDbPropertyDefinition> property : this.properties.entrySet()) {
			String key = property.getKey();
			SerpDbPropertyDefinition value = property.getValue();
			if(value.hasDefault()){
				propertiesWithDefaultValue.add(key);
			}
		}
		return propertiesWithDefaultValue;
	}
	
	public HashMap<String, SerpDbPropertyDefinition> getProperties(){
		return this.properties;
	}
}
