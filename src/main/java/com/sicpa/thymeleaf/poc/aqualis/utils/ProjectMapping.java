package com.sicpa.thymeleaf.poc.aqualis.utils;

/**
 * Class with the url mappings for each part of the project
 *
 */

public final class ProjectMapping {
	
	public static final String ACTIVE_INACTIVE 	= "active";
	public static final String ADD				= "add";
	public static final String ADMIN			= "admin";
	public static final String DELETE			= "delete/{id}";
	public static final String DETAILS			= "details/{id}";
	public static final String DETAILS_PAGE		= "details";
	public static final String EDIT				= "edit/{id}";
	public static final String EDIT_PAGE		= "edit";
	public static final String LIST				= "list";
	public static final String SAVE				= "save";
	public static final String UPDATE			= "update";
	public static final String REPORT   		= "report";
	public static final String CANCEL			= "cancel/{id}";
	public static final String BLOCK			= "block/{id}";
	
	private ProjectMapping() {}
	
	public final class REQUEST_ACCESS{
		public static final String BASE_MAPPING = "requestaccess";
		public static final String REQUEST_NEW_PASSWORD = BASE_MAPPING + "/requestNewPassword/{requestCode}";
		public static final String FORM = BASE_MAPPING + "/form";
		public static final String CREATE_NEW_PASSWORD = BASE_MAPPING + "/createNewPassword";
		private REQUEST_ACCESS() {}
	}
	
	public final class USER {
		public static final String BASE_MAPPING = "user";
		private USER() {}
	}
	
	public final class CURSILHISTA {
		public static final String BASE_MAPPING = "cursilhista";
		private CURSILHISTA() {}
	}
	
	public final class CANDIDATE {
		public static final String BASE_MAPPING = "candidate";
		private CANDIDATE() {}
	}
	
	public final class REPORT_ {
		public static final String BASE_MAPPING = "report";
		private REPORT_() {}
	}
	
	public final class RETREAT_HOUSE {
		public static final String BASE_MAPPING = "retreathouse";
		private RETREAT_HOUSE() {}
	}
	
	public final class RETREAT {
		public static final String BASE_MAPPING = "retreat";
		private RETREAT() {}
	}
		
	public final class LOGIN {
		public static final String BASE_MAPPING = "login";
		private LOGIN() {}
	}
				
	/**
	 * Specific mappings for reports
	 *
	 */
	public final class AUDIT {
		public static final String BASE_MAPPING = "audit";
		public static final String PAGE_ACTIONS = "pageActions";
		private AUDIT() {}
	}
	
	public final class PROFILE {
		public static final String USER 	= "PROFILE_USER";
		public static final String ADMIN 	= "PROFILE_ADMIN";
		public static final String SECRETARY= "PROFILE_SECRETARY";
		private PROFILE() {}
	}
	
}
