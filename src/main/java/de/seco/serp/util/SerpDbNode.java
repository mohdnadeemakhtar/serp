package de.seco.serp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SerpDbNode {
	HashMap<String, Object> properties;
	String label;
	SerpDbNodeDefinition definition;
	
	public SerpDbNode(String label, HashMap<String, String> properties){
		SerpDbSchemaDefinition schemaDefinition = SerpDbSchemaDefinition.getInstance();
		this.definition = schemaDefinition.getNodeType(label);
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
		
		
	}
	
	private void setPropertyDefaultValue(String propertyName) {
		
		
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
	
}
