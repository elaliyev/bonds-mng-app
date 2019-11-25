package com.bonds.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

	private Object fieldName;
	private Object fieldValue;
	private String entityName;
	
	
	public NotFoundException(Object fieldName, Object fieldValue, String entityName) {
		 super(String.format("%s not found with %s : '%s'", entityName, fieldName, fieldValue));
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.entityName = entityName;
	}
	public Object getFieldName() {
		return fieldName;
	}
	public void setFieldName(Object fieldName) {
		this.fieldName = fieldName;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	
}
