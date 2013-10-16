package de.seco.serp.util;

import org.codehaus.jackson.JsonNode;

public class SerpDbPropertyDefinition {
	String displayLabel;
	//TODO: change to real data types
	Class type; 
	Object defaultValue;
	boolean required;
	
	
	
	public SerpDbPropertyDefinition(JsonNode jsonNode) {
		this.displayLabel = null;
		this.type = null;
		this.defaultValue = null;
		this.required = false;
		
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
			this.setDefaultValue(defaultValueNode);
		}
		
		JsonNode requiredNode = jsonNode.get("required");
		if(requiredNode != null){
			this.required = requiredNode.getBooleanValue();
		}
		
	}
	
	public boolean validateValue(String value){

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
		
		return this.required;
	}

	public boolean hasDefault() {
		
		return (this.defaultValue != null);
	}

	private void setDefaultValue(JsonNode defaultValueNode){
		if(this.type.equals(String.class)){
			this.defaultValue = defaultValueNode.getTextValue();
		} else if (this.type.equals(Integer.class)){
			this.defaultValue = defaultValueNode.getIntValue();
		} else if (this.type.equals(Boolean.class)){
			this.defaultValue = defaultValueNode.getBooleanValue();
		}
	}
	
	public Object getDefaultValue(){
		return this.defaultValue;
	}
	
	public Class getType() {
		
		return this.type;
	}

	public String getDisplayLabel() {
		return displayLabel;
	}
	
//	public String toString(){
//		return "\ndisplayLabel: "+displayLabel+" type:"+type+"\n";
//	}
	
}
