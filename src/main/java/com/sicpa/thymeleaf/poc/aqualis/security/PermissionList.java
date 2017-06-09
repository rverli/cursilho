package com.sicpa.thymeleaf.poc.aqualis.security;

/**
 * Describes All permissions of application.
 *
 */
public final class PermissionList {

	private static final String PREFIX			= "ROLE_";
	private static final String ADMIN			= PREFIX + "ADMIN";
	private static final String LIST			= PREFIX + "LIST";
	private static final String ADD 			= PREFIX + "ADD";
	private static final String EDIT 			= PREFIX + "EDIT";
	private static final String DELETE 			= PREFIX + "DELETE";
	private static final String DETAILS 		= PREFIX + "DETAILS";
	private static final String ACTIVE_INACTIVE = PREFIX + "ACTIVE_INACTIVE";
	private static final String REPORT 			= PREFIX + "REPORT";
	
	/**
	 * User Permissions
	 *
	 */
	public static final class USER {
		private static final String SUFIX 	= "_USER";
		
		public static final String ADMIN			= PermissionList.ADMIN + SUFIX;
		public static final String LIST				= PermissionList.LIST + SUFIX;
		public static final String ADD 				= PermissionList.ADD + SUFIX;
		public static final String EDIT 			= PermissionList.EDIT + SUFIX;
		public static final String DELETE 			= PermissionList.DELETE + SUFIX;
		public static final String DETAILS 			= PermissionList.DETAILS + SUFIX;
		public static final String ACTIVE_INACTIVE	= PermissionList.ACTIVE_INACTIVE + SUFIX;
		public static final String REPORT 			= PermissionList.REPORT + SUFIX;
	}
		
	/**
	 * Cursilhista Permissions
	 *
	 */
	public static final class CURSILHISTA {
		private static final String SUFIX 	= "_CURSILHISTA";
		
		public static final String ADMIN			= PermissionList.ADMIN + SUFIX;
		public static final String LIST				= PermissionList.LIST + SUFIX;
		public static final String ADD 				= PermissionList.ADD + SUFIX;
		public static final String EDIT 			= PermissionList.EDIT + SUFIX;
		public static final String DELETE 			= PermissionList.DELETE + SUFIX;
		public static final String DETAILS 			= PermissionList.DETAILS + SUFIX;
		public static final String ACTIVE_INACTIVE	= PermissionList.ACTIVE_INACTIVE + SUFIX;
		public static final String REPORT 			= PermissionList.REPORT + SUFIX;
	}
	
	/**
	 * Candidate Permissions
	 *
	 */
	public static final class CANDIDATE {
		private static final String SUFIX 	= "_CANDIDATE";
		
		public static final String ADMIN			= PermissionList.ADMIN + SUFIX;
		public static final String LIST				= PermissionList.LIST + SUFIX;
		public static final String ADD 				= PermissionList.ADD + SUFIX;
		public static final String EDIT 			= PermissionList.EDIT + SUFIX;
		public static final String DELETE 			= PermissionList.DELETE + SUFIX;
		public static final String DETAILS 			= PermissionList.DETAILS + SUFIX;
		public static final String ACTIVE_INACTIVE	= PermissionList.ACTIVE_INACTIVE + SUFIX;
		public static final String REPORT 			= PermissionList.REPORT + SUFIX;
	}
	
	/**
	 * Retreat Permissions
	 *
	 */
	public static final class RETREAT {
		private static final String SUFIX 	= "_RETREAT";
		
		public static final String ADMIN			= PermissionList.ADMIN + SUFIX;
		public static final String LIST				= PermissionList.LIST + SUFIX;
		public static final String ADD 				= PermissionList.ADD + SUFIX;
		public static final String EDIT 			= PermissionList.EDIT + SUFIX;
		public static final String DELETE 			= PermissionList.DELETE + SUFIX;
		public static final String DETAILS 			= PermissionList.DETAILS + SUFIX;
		public static final String ACTIVE_INACTIVE	= PermissionList.ACTIVE_INACTIVE + SUFIX;
		public static final String REPORT 			= PermissionList.REPORT + SUFIX;
	}
	
	/**
	 * Report Permissions
	 *
	 */
	public static final class REPORT {
		private static final String SUFIX 	= "_REPORTJASPER";
		
		public static final String ADMIN			= PermissionList.ADMIN + SUFIX;
	}
	
	/**
	 * RetreatHouse Permissions
	 *
	 */
	public static final class RETREAT_HOUSE {
		private static final String SUFIX 	= "_RETREAT_HOUSE";
		
		public static final String ADMIN			= PermissionList.ADMIN + SUFIX;
		public static final String LIST				= PermissionList.LIST + SUFIX;
		public static final String ADD 				= PermissionList.ADD + SUFIX;
		public static final String EDIT 			= PermissionList.EDIT + SUFIX;
		public static final String DELETE 			= PermissionList.DELETE + SUFIX;
		public static final String DETAILS 			= PermissionList.DETAILS + SUFIX;
		public static final String ACTIVE_INACTIVE	= PermissionList.ACTIVE_INACTIVE + SUFIX;
		public static final String REPORT 			= PermissionList.REPORT + SUFIX;
	}
		
	/**
	 * Login Permissions
	 *
	 */
	public static final class LOGIN {
		private static final String SUFIX 	= "_LOGIN";
		
		public static final String LOGIN			= PermissionList.PREFIX + "LOGIN";
		public static final String LOGOUT			= PermissionList.PREFIX + "LOGOUT";
		public static final String EDIT 			= PermissionList.EDIT + SUFIX;
	}
	
	/**
	 * Audit Permissions
	 */
	public static final class AUDIT {
		private static final String SUFIX 	= "_AUDIT";
		
		public static final String REPORT 			= PermissionList.REPORT + SUFIX;
	}
}