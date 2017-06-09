package com.sicpa.thymeleaf.poc.aqualis.enumerator;

/**
 * 
 * @author Gsalomao
 *
 */
public enum AuditingOperationType {
	
	ADD("app.audit.operation.add"),
	EDIT("app.audit.operation.edit"),
	DELETE("app.audit.operation.delete"), 
	ACTIVE_INACTIVE("app.audit.operation.active_inactive"),
	
	// ORDER ACTIONS
	REJECT("app.audit.operation.reject"),
	CANCEL("app.audit.operation.cancel"),
	APPROVE("app.audit.operation.aprove"),
	BLOCK("app.audit.operation.block"),
	DELIVER("app.audit.operation.delivering"),
	RECEIVE("app.audit.operation.receiving"),
	
	//AUTHENTICATION ACTIONS
	LOGIN("app.audit.operation.login"),
	LOGOUT("app.audit.operation.logout");
	
	private final String label;
	
	private AuditingOperationType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
