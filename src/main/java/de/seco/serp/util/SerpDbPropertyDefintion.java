package de.seco.serp.util;

import org.codehaus.jackson.JsonNode;

public class SerpDbPropertyDefintion {
	String displayLabel;
	//TODO: change to real data types
	String type; 
	String defaultValue;
	String required;
	
	
	
	public SerpDbPropertyDefintion(JsonNode jsonNode) {
		this.displayLabel = null;
		this.type = null;
		this.defaultValue = null;
		this.required = null;
		
		JsonNode displayLabelNode = jsonNode.get("display_label");
		if(displayLabelNode != null){
			this.displayLabel = displayLabelNode.getTextValue();
		}
		JsonNode typeNode = jsonNode.get("type");
		if(typeNode != null){
			this.type = typeNode.getTextValue();
		}
		JsonNode defaultValueNode = jsonNode.get("default");
		if(defaultValueNode != null){
			this.defaultValue = defaultValueNode.getTextValue();
		}
		JsonNode requiredNode = jsonNode.get("required");
		if(requiredNode != null){
			this.required = requiredNode.getTextValue();
		}
		System.out.println("property definition: "+this.toString());
	}
	
	public String toString(){
		return "displayLabel: "+displayLabel+" type:"+type;
	}
	
}
