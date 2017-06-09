package com.sicpa.thymeleaf.poc.aqualis.messages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemMessages {

	public static final String MESSAGE_ATTRIBUTE = "message";
	public static final String MSG_PARAMETER_ID_IS_NULL = "Parameter 'id' is Null";

	public static int ITEMS_PER_PAGE;

	public static Integer STAMP_LOT_SIZE;

	public static String INVALID_PARAMETER;

	public static String SYSTEM_ERROR;

	public static String CNPJ_UNIQUE;

	public static String CPF_UNIQUE;

	// User messages
	public static String USER_REMOVE_ERRO;
	public static String USER_REMOVE_SUCESS;
	public static String USER_SAVE_SUCCESS;
	public static String USER_UPDATE_SUCCESS;
	public static String USER_UPDATE_ERRO;
	public static String USER_SERVICE_ERROR_FIND_USER_WITH_PARAMETERS_KEY;
	public static String USER_SERVICE_USER_DELETE_ERROR_KEY;
	public static String USER_SERVICE_MAIL_UNIQUE_KEY;
	public static String USER_SERVICE_CPF_UNIQUE_KEY;
	public static String USER_SERVICE_USER_SAVE_ERROR_KEY;
	public static String USER_SERVICE_ACTIVATE_SAVE_ERROR_KEY;
	public static String USER_SERVICE_USER_NOTFOUND_KEY;
	public static String USER_SERVICE_USER_EDIT_ERROR_KEY;
	public static String USER_REQUEST_ACCESS_SUCCESS;
	public static String USER_SERVICE_ERROR_UPDATE_PASS_KEY;
	public static String USER_SERVICE_DUPLICATED_USER_PASS_KEY;
	public static String USER_SERVICE_WRONG_USER_PASS;
	
	// Company messages
	public static String COMPANY_LIST_ERRO;

	public static String COMPANY_SAVE_SUCCESS;
	public static String COMPANY_PARAMETER_IS_NULL;
	public static String COMPANY_SAVE_ERROR;
	public static String COMPANY_ERROR_FIND_COMPANY;
	public static String COMPANY_UPDATE_SUCCESS;
	public static String COMPANY_UPDATE_ERRO;
	public static String COMPANY_REMOVE_SUCESS;
	public static String COMPANY_REMOVE_ERRO;
	public static String COMPANY_DETAIL_ERROR;

	public static String COMPANY_SERVICE_NOT_FOUND;
	public static String COMPANY_SAVE_ACTIVATOR_ERROR;

	// Cursilhista messages
	//private static String CURSILHISTA_LIST_ERRO;

	public static String CURSILHISTA_SAVE_SUCCESS;
	public static String CURSILHISTA_PARAMETER_IS_NULL;
	public static String CURSILHISTA_SAVE_ERROR;
	public static String CURSILHISTA_ERROR_FIND_COMPANY;
	public static String CURSILHISTA_UPDATE_SUCCESS;
	public static String CURSILHISTA_UPDATE_ERRO;
	public static String CURSILHISTA_REMOVE_SUCESS;
	public static String CURSILHISTA_REMOVE_ERRO;
	public static String CURSILHISTA_DETAIL_ERROR;

	public static String CURSILHISTA_SERVICE_NOT_FOUND;
	public static String CURSILHISTA_SAVE_ACTIVATOR_ERROR;

	//RETREAT_HOUSE messages
	public static String RETREAT_HOUSE_LIST_ERRO;

	public static String RETREAT_HOUSE_SAVE_SUCCESS;
	public static String RETREAT_HOUSE_PARAMETER_IS_NULL; 
	public static String RETREAT_HOUSE_SAVE_ERROR; 
	public static String RETREAT_HOUSE_ERROR_FIND_COMPANY; 
	public static String RETREAT_HOUSE_UPDATE_SUCCESS;
	public static String RETREAT_HOUSE_UPDATE_ERRO;
	public static String RETREAT_HOUSE_REMOVE_SUCESS;
	public static String RETREAT_HOUSE_REMOVE_ERRO;
	public static String RETREAT_HOUSE_DETAIL_ERROR;

	public static String RETREAT_HOUSE_SERVICE_NOT_FOUND;
	public static String RETREAT_HOUSE_SAVE_ACTIVATOR_ERROR; 
	
	//RETREAT_HOUSE messages
	public static String RETREAT_LIST_ERRO;

	public static String RETREAT_SAVE_SUCCESS;
	public static String RETREAT_PARAMETER_IS_NULL; 
	public static String RETREAT_SAVE_ERROR; 
	public static String RETREAT_ERROR_FIND_COMPANY; 
	public static String RETREAT_UPDATE_SUCCESS;
	public static String RETREAT_UPDATE_ERRO;
	public static String RETREAT_REMOVE_SUCESS;
	public static String RETREAT_REMOVE_ERRO;
	public static String RETREAT_DETAIL_ERROR;

	public static String RETREAT_SERVICE_NOT_FOUND;
	public static String RETREAT_SAVE_ACTIVATOR_ERROR; 
	
	// ENTITY messages
	public static String ENTITY_SAVE_SUCCESS;
	public static String ENTITY_UPDATE_SUCCESS;
	public static String ENTITY_REMOVE_SUCCESS;

	public static String ENTITY_PARAMETER_IS_NULL;
	public static String ENTITY_APP_ERROR_FIND;
	public static String ENTITY_NOT_FOUND_ERRO;
	public static String ENTITY_SAVE_ERROR;
	public static String ENTITY_REMOVE_ERRO;
	public static String ENTITY_UPDATE_ERRO;
	public static String ENTITY_DETAIL_ERROR;
	
	//UserRequestAccess messages
	public static String USER_REQUEST_ACCESS_SAVE_ERROR;
	public static String USER_REQUEST_ACCESS_NEW_PASSWORD;
	
	// BRAND messages
	public static String BRAND_UNIQUE;
	public static String BRAND_PRODUCT_TYPE_DUPLICATED;
	public static String BRAND_EDIT_ERROR;

	// ORDER messages
	public static String ORDER_BLOCK_SUCCESS;
	public static String ORDER_REJECT_SUCCESS;
	public static String ORDER_DELIVER_SUCCESS;
	public static String ORDER_APPROVE_SUCCESS;
	public static String ORDER_RECEIVE_SUCCESS;
	public static String ORDER_SAVE_SUCCESS;
	public static String ORDER_CANCEL_SUCCESS;
	public static String ORDER_NOT_DELIVERED_ERROR;
	public static String ORDER_NOT_APPROVED;
	public static String ORDER_CANCEL_ERROR;
	public static String ORDER_APPROVE_ERROR;
	public static String ORDER_RECEIVE_ERROR;
	public static String ORDER_DELIVER_ERROR;
	public static String ORDER_REJECT_ERROR;
	public static String ORDER_BLOCK_ERROR;
	public static String ORDER_LIST_ERROR;
	public static String ENTITY_FIND_USER_ROL_ERROR;
	public static String ORDER_USER_DIFFERS_FROM_CURRENT_USER;
	public static String ORDER_USER_COMPANY_DIFFERS_FROM_BRAND_COMPANY;
	public static String ORDER_USER_HAS_NO_COMPANY;
	public static String ORDER_HAS_NO_BRAND;
	public static String ORDER_INVALID_STATUS;
	public static String INVALID_USER_STATE;

	// STAMP Messages
	public static String STAMP_VALIDATE_SUCCES;

	// PAGE messages
	public static String APP_SERVICE_ERROR_FIND_PAGE_WITH_PARAMETERS_KEY;

	// STAMP messages
	public static String STAMP_CODE_AND_RANDOM_CODE_NOT_INFORMED;
	public static String STAMP_CODE_INFORMED_AND_RANDOM_CODE_NOT_INFORMED_OR_INVALID;
	public static String STAMP_NUMBER_NOT_INFORMED_AND_RANDOM_CODE_INFORMED;
	public static String STAMP_CODE_AND_RANDOM_CODE_INFORMED;
	public static String STAMP_NOT_FOUNDED;
	public static String ORDER_BRAND_IS_INACTIVE;

	// STAMPBYCOMPANY messages
	public static String STAMPBYCOMPANY_SERVICE_ERROR_FIND;
	public static String STAMPBYCOMPANY_SERVICE_ERROR_YEAR_LIST;

	private SystemMessages() {
		// Private constructor to static class
	}

	@Value("${app.web.pagination.model.items-per-page:5}")
	public void setItemsPerPage(int itemsPerPage) {
		ITEMS_PER_PAGE = itemsPerPage;
	}

	@Value("${app.web.stamp.lot.size:3000}")
	public void setStampLotSize(int value) {
		STAMP_LOT_SIZE = value;
	}

	@Value("${app.web.userform.remove.error}")
	public void setUserRemoveErro(String userRemoveErro) {
		USER_REMOVE_ERRO = userRemoveErro;
	}

	@Value("${app.web.userform.remove.success}")
	public void setUserRemoveSuccess(String userRemoveSuccess) {
		USER_REMOVE_SUCESS = userRemoveSuccess;
	}

	@Value("${app.web.userform.save.success}")
	public void setUserSaveSuccess(String userSaveSuccess) {
		USER_SAVE_SUCCESS = userSaveSuccess;
	}

	@Value("${app.web.userform.update.success}")
	public void setUserUpdateSuccess(String userUpdateSuccess) {
		USER_UPDATE_SUCCESS = userUpdateSuccess;
	}

	@Value("${app.web.usercontroller.parameter.erro}")
	public void setInvalidParameter(String invalidParameter) {
		INVALID_PARAMETER = invalidParameter;
	}

	@Value("${app.web.usercontroller.erro}")
	public void setUserUpdateErro(String userUpdateErro) {
		USER_UPDATE_ERRO = userUpdateErro;
	}

	@Value("${app.web.erro}")
	public void setSystemErro(String systemErro) {
		SYSTEM_ERROR = systemErro;
	}

	@Value("${app.web.companycontroller.erro}")
	public void setCompanyListErro(String companyListErro) {
		COMPANY_LIST_ERRO = companyListErro;
	}

	@Value("${app.web.entity.form.save.success}")
	public void setSaveSuccess(String companySaveSuccess) {
		ENTITY_SAVE_SUCCESS = companySaveSuccess;
	}

	@Value("${app.service.entity.save.error}")
	public void setEntitySaveError(String companySaveError) {
		ENTITY_SAVE_ERROR = companySaveError;
	}

	@Value("${app.service.cnpj.unique}")
	public void setCNPJUnique(String cNPJUnique) {
		CNPJ_UNIQUE = cNPJUnique;
	}

	@Value("${app.service.cpf.unique}")
	public void setCPFUnique(String cNPJUnique) {
		CPF_UNIQUE = cNPJUnique;
	}

	@Value("${app.service.error.find.company}")
	public void setComapnyAppErroFindCompany(String comapnyAppErroFindCompany) {
		COMPANY_ERROR_FIND_COMPANY = comapnyAppErroFindCompany;
	}

	@Value("${app.service.entity.find.error}")
	public void setEntityAppErroFindEntity(String entityAppErroFind) {
		ENTITY_APP_ERROR_FIND = entityAppErroFind;
	}

	@Value("${app.web.entity.form.parameter.null}")
	public void setEntityIsNull(String companyIsNull) {
		ENTITY_PARAMETER_IS_NULL = companyIsNull;
	}

	@Value("${app.web.entity.form.detail.error}")
	public static void setEntityDetailError(String entityDetailError) {
		ENTITY_DETAIL_ERROR = entityDetailError;
	}

	@Value("${app.web.entity.form.update.success}")
	public void setEntityUpdateSucces(String entityUpdateSucces) {
		ENTITY_UPDATE_SUCCESS = entityUpdateSucces;
	}

	@Value("${app.web.entity.form.update.erro}")
	public void setEntityUpdateErro(String entityUpdateErro) {
		ENTITY_UPDATE_ERRO = entityUpdateErro;
	}

	@Value("${app.web.entity.form.notfound.erro}")
	public void setEntityNotFoundErro(String entityNotFoundErro) {
		ENTITY_NOT_FOUND_ERRO = entityNotFoundErro;
	}

	@Value("${app.web.entity.form.remove.error}")
	public void setEntityRemoveErro(String entityRemoveErro) {
		ENTITY_REMOVE_ERRO = entityRemoveErro;
	}

	@Value("${app.web.company.form.parameter.null}")
	public void setCompanyIsNull(String companyIsNull) {
		COMPANY_PARAMETER_IS_NULL = companyIsNull;
	}

	@Value("${app.web.companycontroller.detail.error}")
	public static void setCompanyDetailError(String companyDetailError) {
		COMPANY_DETAIL_ERROR = companyDetailError;
	}

	@Value("${app.web.company.form.update.success}")
	public void setCompanyUpdateSucces(String companyUpdateSucces) {
		COMPANY_UPDATE_SUCCESS = companyUpdateSucces;
	}

	@Value("${app.web.company.form.update.erro}")
	public void setCompanyUpdateErro(String companyUpdateErro) {
		COMPANY_UPDATE_ERRO = companyUpdateErro;
	}

	@Value("${app.web.company.form.remove.success}")
	public void setCompanyrRemoveSuccess(String companyRemoveSuccess) {
		COMPANY_REMOVE_SUCESS = companyRemoveSuccess;
	}

	@Value("${app.web.company.form.remove.error}")
	public void setCompanyRemoveErro(String companyRemoveErro) {
		COMPANY_REMOVE_ERRO = companyRemoveErro;
	}

	@Value("${app.service.company.notfound}")
	public void setCompanyServiceNotFound(String companyServiceNotFound) {
		COMPANY_SERVICE_NOT_FOUND = companyServiceNotFound;
	}

	@Value("${app.service.company.save.ativate.error}")
	public void setCompanyServiceActivateError(String companyServiceActivateError) {
		COMPANY_SAVE_ACTIVATOR_ERROR = companyServiceActivateError;
	}

	@Value("${app.service.error.find.user.with.parameters}")
	public void setUserServiceErrorFindUserWithParametersKey(String uSER_SERVICE_ERROR_FIND_USER_WITH_PARAMETERS_KEY) {
		USER_SERVICE_ERROR_FIND_USER_WITH_PARAMETERS_KEY = uSER_SERVICE_ERROR_FIND_USER_WITH_PARAMETERS_KEY;
	}

	@Value("${app.service.user.delete.error}")
	public void setUserServiceUserDeleteErrorKey(String uSER_SERVICE_USER_DELETE_ERROR_KEY) {
		USER_SERVICE_USER_DELETE_ERROR_KEY = uSER_SERVICE_USER_DELETE_ERROR_KEY;
	}

	@Value("${app.service.mail.unique}")
	public void setUserServiceMailUniqueKey(String uSER_SERVICE_MAIL_UNIQUE_KEY) {
		USER_SERVICE_MAIL_UNIQUE_KEY = uSER_SERVICE_MAIL_UNIQUE_KEY;
	}

	@Value("${app.service.cpf.unique}")
	public void setUserServiceCpfUniqueKey(String uSER_SERVICE_CPF_UNIQUE_KEY) {
		USER_SERVICE_CPF_UNIQUE_KEY = uSER_SERVICE_CPF_UNIQUE_KEY;
	}

	@Value("${app.service.user.save.error}")
	public void setUserServiceUserSaveErrorKey(String uSER_SERVICE_USER_SAVE_ERROR_KEY) {
		USER_SERVICE_USER_SAVE_ERROR_KEY = uSER_SERVICE_USER_SAVE_ERROR_KEY;
	}

	@Value("${app.service.activate.save.error}")
	public void setUserServiceActiveSaveErrorKey(String uSER_SERVICE_ACTIVATE_SAVE_ERROR_KEY) {
		USER_SERVICE_ACTIVATE_SAVE_ERROR_KEY = uSER_SERVICE_ACTIVATE_SAVE_ERROR_KEY;
	}

	@Value("${app.service.user.notfound}")
	public void setUserServiceUserNotFound(String uSER_SERVICE_USER_NOTFOUND_KEY) {
		USER_SERVICE_USER_NOTFOUND_KEY = uSER_SERVICE_USER_NOTFOUND_KEY;
	}

	@Value("${app.service.user.edit.error}")
	public void setUserServiceUserEditErroKey(String uSER_SERVICE_USER_EDIT_ERROR_KEY) {
		USER_SERVICE_USER_EDIT_ERROR_KEY = uSER_SERVICE_USER_EDIT_ERROR_KEY;
	}

	@Value("${app.web.entity.form.delete.success}")
	public void setENTITY_REMOVE_SUCCESS(String eNTITY_REMOVE_SUCCESS) {
		ENTITY_REMOVE_SUCCESS = eNTITY_REMOVE_SUCCESS;
	}

	@Value("${app.service.error.find.with.parameters.key}")
	public void setServiceErrorFindPageWithParametersKey(String appServiceErrorFindPageWithParametersKey) {
		APP_SERVICE_ERROR_FIND_PAGE_WITH_PARAMETERS_KEY = appServiceErrorFindPageWithParametersKey;
	}

	@Value("${app.service.brand.product.type.duplicated}")
	public void setBrandProductTypeDuplicated(String brandProductTypeDuplicated) {
		BRAND_PRODUCT_TYPE_DUPLICATED = brandProductTypeDuplicated;
	}

	@Value("${app.service.brand.edit.error}")
	public void setBrandEditeError(String value) {
		BRAND_EDIT_ERROR = value;
	}

	@Value("${app.service.order.succes.block}")
	public void setORDER_BLOCK_SUCCESS(String value) {
		ORDER_BLOCK_SUCCESS = value;
	}

	@Value("${app.service.order.succes.reject}")
	public void setORDER_REJECT_SUCCESS(String value) {
		ORDER_REJECT_SUCCESS = value;
	}

	@Value("${app.service.order.succes.deliver}")
	public void setORDER_DELIVER_SUCCESS(String value) {
		ORDER_DELIVER_SUCCESS = value;
	}

	@Value("${app.service.order.succes.approve}")
	public void setORDER_APPROVE_SUCCESS(String value) {
		ORDER_APPROVE_SUCCESS = value;
	}

	@Value("${app.service.order.succes.receive}")
	public void setORDER_RECEIVE_SUCCESS(String value) {
		ORDER_RECEIVE_SUCCESS = value;
	}

	@Value("${app.service.order.succes.cancel}")
	public void setORDER_CANCEL_SUCCESS(String value) {
		ORDER_CANCEL_SUCCESS = value;
	}

	@Value("${app.service.order.succes.save}")
	public void setORDER_SAVE_SUCCESS(String value) {
		ORDER_SAVE_SUCCESS = value;
	}

	@Value("${app.service.order.error.notdelivered}")
	public void setOrderNotDeliveredError(String message) {
		ORDER_NOT_DELIVERED_ERROR = message;
	}

	@Value("${app.service.order.error.cancel}")
	public void setOrderCancelError(String message) {
		ORDER_CANCEL_ERROR = message;
	}

	@Value("${app.service.order.error.approve}")
	public void setOrderApproveError(String message) {
		ORDER_APPROVE_ERROR = message;
	}

	@Value("${app.service.order.error.receive}")
	public void setOrderReceiveError(String message) {
		ORDER_RECEIVE_ERROR = message;
	}

	@Value("${app.service.order.error.deliver}")
	public void setOrderDeliverError(String message) {
		ORDER_DELIVER_ERROR = message;
	}

	@Value("${app.service.order.error.reject}")
	public void setOrderRejectError(String message) {
		ORDER_REJECT_ERROR = message;
	}

	@Value("${app.service.order.error.notapproved}")
	public void setOrderNotApproved(String message) {
		ORDER_NOT_APPROVED = message;
	}

	@Value("${app.service.order.error.block}")
	public void setOrderBlockError(String message) {
		ORDER_BLOCK_ERROR = message;
	}

	@Value("${app.service.brand.duplicated}")
	public void setBrandUnique(String message) {
		BRAND_UNIQUE = message;
	}

	@Value("${app.service.order.find.user.rol.error}")
	public void setEntityFindUserRolError(String orderFindUserRolError) {
		ENTITY_FIND_USER_ROL_ERROR = orderFindUserRolError;
	}

	@Value("${app.service.stamp.stampnumber.and.randomcode.not.informed}")
	public void setStampNumberAndRandomCodeNotInformed(String message) {
		STAMP_CODE_AND_RANDOM_CODE_NOT_INFORMED = message;
	}

	@Value("${app.service.stamp.stampnumber.not.informed.and.randomcode.informed}")
	public void setStampNumberNotInformedAndRandomInformed(String message) {
		STAMP_NUMBER_NOT_INFORMED_AND_RANDOM_CODE_INFORMED = message;
	}

	@Value("${app.service.stamp.stamnumber.informed.and.randomcode.not.informed.or.invalid}")
	public void setStampNumberInformedAndRandomCodeNotInformedOrInvalid(String message) {
		STAMP_CODE_INFORMED_AND_RANDOM_CODE_NOT_INFORMED_OR_INVALID = message;
	}

	@Value("${app.service.stamp.stampnumber.and.randomcode.informed}")
	public void setStampNumberAndRandomCodeInformed(String message) {
		STAMP_CODE_AND_RANDOM_CODE_INFORMED = message;
	}

	@Value("${app.service.stamp.not.founded}")
	public void setStampNotFounded(String message) {
		STAMP_NOT_FOUNDED = message;
	}

	@Value("${app.service.stamp.validate.succes}")
	public void setStampValidateSucces(String message) {
		STAMP_VALIDATE_SUCCES = message;
	}

	@Value("${app.web.ordercontroller.list.erro}")
	public void setOrderListError(String message) {
		ORDER_LIST_ERROR = message;
	}

	@Value("${app.web.order.msg.order.user.differs.from.current.user}")
	public void setORDER_USER_DIFFERS_FROM_CURRENT_USER(String value) {
		ORDER_USER_DIFFERS_FROM_CURRENT_USER = value;
	}

	@Value("${app.web.order.msg.order.user.company.differs.from.brand.company}")
	public void setORDER_USER_COMPANY_DIFFERS_FROM_BRAND_COMPANY(String value) {
		ORDER_USER_COMPANY_DIFFERS_FROM_BRAND_COMPANY = value;
	}

	@Value("${app.web.order.msg.order.user.has.no.company}")
	public void setORDER_USER_HAS_NO_COMPANY(String value) {
		ORDER_USER_HAS_NO_COMPANY = value;
	}

	@Value("${app.web.order.msg.order.has.no.brand}")
	public void setORDER_HAS_NO_BRAND(String value) {
		ORDER_HAS_NO_BRAND = value;
	}

	@Value("${app.web.user.msg.invalid.user.state}")
	public void setINVALID_USER_STATE(String value) {
		INVALID_USER_STATE = value;
	}

	@Value("${app.web.order.msg.order.brand.is.inactive}")
	public void setORDER_BRAND_IS_INACTIVE(String value) {
		ORDER_BRAND_IS_INACTIVE = value;
	}

	@Value("${app.service.order.status.invalid}")
	public void setORDER_INVALID_STATUS(String value) {
		ORDER_INVALID_STATUS = value;
	}

	@Value("${app.service.stampbycompany.error.find}")
	public void setSTAMPBYCOMPANY_SERVICE_ERROR_FIND(String value) {
		STAMPBYCOMPANY_SERVICE_ERROR_FIND = value;
	}

	@Value("${app.service.stampbycompany.error.year.list}")
	public void setSTAMPBYCOMPANY_SERVICE_ERROR_YEAR_LIST(String value) {
		STAMPBYCOMPANY_SERVICE_ERROR_YEAR_LIST = value;
	}

	// ******************

	@Value("${app.web.cursilhista.form.parameter.null}")
	public void setCursilhistaIsNull(String companyIsNull) {
		CURSILHISTA_PARAMETER_IS_NULL = companyIsNull;
	}

	@Value("${app.web.cursilhistacontroller.detail.error}")
	public static void setCursilhistaDetailError(String companyDetailError) {
		CURSILHISTA_DETAIL_ERROR = companyDetailError;
	}

	@Value("${app.web.cursilhista.form.update.success}")
	public void setCursilhistaUpdateSucces(String companyUpdateSucces) {
		CURSILHISTA_UPDATE_SUCCESS = companyUpdateSucces;
	}

	@Value("${app.web.cursilhista.form.update.erro}")
	public void setCursilhistaUpdateErro(String companyUpdateErro) {
		CURSILHISTA_UPDATE_ERRO = companyUpdateErro;
	}

	@Value("${app.web.cursilhista.form.remove.success}")
	public void setCursilhistarRemoveSuccess(String companyRemoveSuccess) {
		CURSILHISTA_REMOVE_SUCESS = companyRemoveSuccess;
	}

	@Value("${app.web.cursilhista.form.remove.error}")
	public void setCursilhistaRemoveErro(String companyRemoveErro) {
		CURSILHISTA_REMOVE_ERRO = companyRemoveErro;
	}

	@Value("${app.service.cursilhista.notfound}")
	public void setCursilhistaServiceNotFound(String companyServiceNotFound) {
		CURSILHISTA_SERVICE_NOT_FOUND = companyServiceNotFound;
	}

	@Value("${app.service.cursilhista.save.ativate.error}")
	public void setCursilhistaServiceActivateError(String companyServiceActivateError) {
		CURSILHISTA_SAVE_ACTIVATOR_ERROR = companyServiceActivateError;
	}

	//@Value("${app.web.cursilhistacontroller.erro}")
	//public void setCursilhistaListErro(String companyListErro) {
	//	CURSILHISTA_LIST_ERRO = companyListErro;
	//}

	@Value("${app.service.error.find.company}")
	public void setCursilhistaAppErroFindCursilhista(String comapnyAppErroFindCompany) {
		CURSILHISTA_ERROR_FIND_COMPANY = comapnyAppErroFindCompany;
	}
	
	//*******************************************
	@Value("${app.web.retreathouse.form.parameter.null}")
	public void setRetreatHouseIsNull(String companyIsNull) {
		RETREAT_HOUSE_PARAMETER_IS_NULL = companyIsNull;
	}

	@Value("${app.web.retreathousecontroller.detail.error}")
	public static void setRetreatHouseDetailError(String companyDetailError) {
		RETREAT_HOUSE_DETAIL_ERROR = companyDetailError;
	}
	
	@Value("${app.web.retreathouse.form.update.success}")
	public void setRetreatHouseUpdateSucces(String companyUpdateSucces){
		RETREAT_HOUSE_UPDATE_SUCCESS = companyUpdateSucces;
	}

	@Value("${app.web.retreathouse.form.update.erro}")
	public void setRetreatHouseUpdateErro(String companyUpdateErro) {
		RETREAT_HOUSE_UPDATE_ERRO = companyUpdateErro;
	}
	
	@Value("${app.web.retreathouse.form.remove.success}")
	public void setRetreatHouseRemoveSuccess(String companyRemoveSuccess) {
		RETREAT_HOUSE_REMOVE_SUCESS = companyRemoveSuccess;
	}

	@Value("${app.web.retreathouse.form.remove.error}")
	public void setRetreatHouseRemoveErro(String companyRemoveErro) {
		RETREAT_HOUSE_REMOVE_ERRO = companyRemoveErro;
	}
	
	@Value("${app.service.retreathouse.notfound}")
	public void setRetreatHouseServiceNotFound(String companyServiceNotFound){
		RETREAT_HOUSE_SERVICE_NOT_FOUND = companyServiceNotFound;
	}

	@Value("${app.service.retreathouse.save.ativate.error}")
	public void setRetreatHouseServiceActivateError(String companyServiceActivateError){
		RETREAT_HOUSE_SAVE_ACTIVATOR_ERROR = companyServiceActivateError;
	}
	
	@Value("${app.web.retreathousecontroller.erro}")
	public void setRetreatHouseListErro(String companyListErro) {
		RETREAT_HOUSE_LIST_ERRO = companyListErro;
	}

	@Value("${app.service.error.find.retreathouse}")
	public void setRetreatHouseAppErroFindCursilhista(String comapnyAppErroFindCompany) {
		RETREAT_HOUSE_ERROR_FIND_COMPANY = comapnyAppErroFindCompany;
	}
	
	//*******************************************
	@Value("${app.web.retreat.form.parameter.null}")
	public void setRetreatIsNull(String companyIsNull) {
		RETREAT_PARAMETER_IS_NULL = companyIsNull;
	}

	@Value("${app.web.retreatcontroller.detail.error}")
	public static void setRetreatDetailError(String detailError) {
		RETREAT_DETAIL_ERROR = detailError;
	}
	
	@Value("${app.web.retreat.form.update.success}")
	public void setRetreatUpdateSucces(String updateSucces){
		RETREAT_UPDATE_SUCCESS = updateSucces;
	}

	@Value("${app.web.retreat.form.update.erro}")
	public void setRetreatUpdateErro(String updateErro) {
		RETREAT_UPDATE_ERRO = updateErro;
	}
	
	@Value("${app.web.retreat.form.remove.success}")
	public void setRetreatRemoveSuccess(String removeSuccess) {
		RETREAT_REMOVE_SUCESS = removeSuccess;
	}

	@Value("${app.web.retreat.form.remove.error}")
	public void setRetreatRemoveErro(String removeErro) {
		RETREAT_REMOVE_ERRO = removeErro;
	}
	
	@Value("${app.service.retreat.notfound}")
	public void setRetreatServiceNotFound(String serviceNotFound){
		RETREAT_SERVICE_NOT_FOUND = serviceNotFound;
	}

	@Value("${app.service.retreat.save.ativate.error}")
	public void setRetreatServiceActivateError(String serviceActivateError){
		RETREAT_SAVE_ACTIVATOR_ERROR = serviceActivateError;
	}
	
	@Value("${app.web.retreatcontroller.erro}")
	public void setRetreatListErro(String listErro) {
		RETREAT_LIST_ERRO = listErro;
	}

	@Value("${app.service.error.find.retreat}")
	public void setRetreatAppErroFindRetreat(String appErroFind) {
		RETREAT_ERROR_FIND_COMPANY = appErroFind;
	}
	
	
	@Value("${app.service.userrequestaccess.save.error}")
	public void setUSER_REQUEST_ACCESS_SAVE_ERROR(String value){
		USER_REQUEST_ACCESS_SAVE_ERROR = value;
	}
	
	@Value("${app.service.userrequestaccess.save.success}")
	public void setUSER_REQUEST_ACCESS_SUCCESS(String value){
		USER_REQUEST_ACCESS_SUCCESS = value;
	}
	
	@Value("${app.service.userrequestaccess.new.password}")
	public void setUSER_REQUEST_ACCESS_NEW_PASSWORD(String value){
		USER_REQUEST_ACCESS_NEW_PASSWORD = value;
	}
	
	@Value("${app.service.error.update.password}")
	public void setUSER_SERVICE_ERROR_UPDATE_PASS_KEY(String value){
		USER_SERVICE_ERROR_UPDATE_PASS_KEY = value;
	}
	
	@Value("${app.service.duplicated.user.password}")
	public void setUSER_SERVICE_DUPLICATED_USER_PASS_KEY(String value){
		USER_SERVICE_DUPLICATED_USER_PASS_KEY = value;
	}
	
	@Value("${app.service.wrong.user.password}")
	public void setUSER_SERVICE_WRONG_USER_PASS(String value){
		USER_SERVICE_WRONG_USER_PASS = value;
	}

	
}
