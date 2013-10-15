package de.seco.serp.util;

import org.codehaus.jackson.JsonNode;

public class SerpDbPropertyDefinition {
	String displayLabel;
	//TODO: change to real data types
	Class type; 
	String defaultValue;
	boolean required;
	
	
	
	public SerpDbPropertyDefinition(JsonNode jsonNode) {
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
			if(typeNode.getTextValue().equals("String")){
				this.type = String.class;
			} else if(typeNode.getTextValue().equals("Integer")){
				this.type = Integer.class;
			} else if(typeNode.getTextValue().equals("Boolean")){
				this.type = Boolean.class;
			} 
		}
		JsonNode defaultValueNode = jsonNode.get("default");
		if(defaultValueNode != null){
			this.defaultValue = defaultValueNode.getTextValue();
		}
		System.out.println("class: "+defaultValueNode.getClass());
		JsonNode requiredNode = jsonNode.get("required");
		if(requiredNode != null){
			this.required = requiredNode.getBooleanValue();
		}
		
	}
	
	public boolean validateValue(String value){
//		TODO: validate value with datatype, required ...
		System.out.println("validate Value: type:"+type);
		if(this.type.equals(String.class)){
			System.out.println("String class");
		} else if (this.type.equals(Integer.class)){
			System.out.println("Integer class");
			try{
				Integer.parseInt(value);
			} catch(NumberFormatException e){
				return false;
			}
		} else if(this.type.equals(Boolean.class)){
			System.out.println("Boolean class");
			if(!value.equals("true") && !value.equals("false")){
				return false;
			}
		} // else more datatypes
		
		
		
		return true;
	}

	public boolean isRequired() {
		
		return required;
	}
	
//	public String toString(){
//		return "\ndisplayLabel: "+displayLabel+" type:"+type+"\n";
//	}
	
}
