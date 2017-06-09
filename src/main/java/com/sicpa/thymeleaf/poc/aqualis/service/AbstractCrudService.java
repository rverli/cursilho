package com.sicpa.thymeleaf.poc.aqualis.service;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sicpa.thymeleaf.poc.aqualis.audit.Auditable;
import com.sicpa.thymeleaf.poc.aqualis.enumerator.AuditingOperationType;
import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.User;
import com.sicpa.thymeleaf.poc.aqualis.utils.AuthUtil;

/**
 * <p>
 * Abstract class with common methods for crud in the Service layer
 * </p>
 * @author ekoetsier
 *
 * @param <T> The entity to be treated by the ServiceImpl
 * @param <I> The Id type of the entity to be treated
 */
public abstract class AbstractCrudService<T, I extends Serializable> {
	
	private JpaRepository<T, I> jpaRepository;
	
	protected AbstractCrudService(JpaRepository<T, I> jpaRepository) {
		this.jpaRepository = jpaRepository;
	}
	
	/**
	 * <p>
	 * Marks an entity for deletion
	 * </p>
	 */
	protected abstract void markEntityForDeletion(T entity) ;
	
	/**
	 * <p>
	 * Validates the entity and throws a {@link ServiceException} if the entity is not valid
	 * </p>
	 */
	protected abstract void validateEntity(T entity) throws ServiceException;

	/**
	 * <p>
	 * Activates or deactivates an entity
	 * </p>
	 */
	protected abstract void activateEntity(T entity);
	

	protected abstract void validateUniqueEntityData(T entity) throws ServiceException;
	

	protected abstract boolean entityExists(T entity) throws ServiceException; 
	
	/**
	 * Method responsible for seeking the entity by id.
	 * @param id
	 * @return Company
	 * @throws ServiceException
	 */
	public T findOne(I id) throws ServiceException {

		T entity;

		if (id == null) {
			throw new IllegalArgumentException(SystemMessages.ENTITY_PARAMETER_IS_NULL);
		}

		try {
			entity = jpaRepository.findOne(id);
		} catch (Exception ex) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, ex);
		}
		return entity;
	}
	
	/**
	 * <p>
	 * Method responsible for saving an entity in the database 
	 * </p>
	 * @param entity
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor=ServiceException.class)
	@Auditable(operationType=AuditingOperationType.ADD)
	public void save(T entity) throws ServiceException {
		
		if (entity == null) {
			throw new IllegalArgumentException(SystemMessages.ENTITY_PARAMETER_IS_NULL);
		}
		
		validateEntity(entity);

		try {
			jpaRepository.saveAndFlush(entity);
		} catch (Exception ex) {
			throw new ServiceException(SystemMessages.ENTITY_SAVE_ERROR, ex);
		}
	}
	
	/**
	 * <p>
	 * Method responsible for marking an entity as removed and saving to the database 
	 * </p>
	 * @param id
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor=ServiceException.class)
	@Auditable(operationType=AuditingOperationType.DELETE)
	public void delete(I id) throws ServiceException {
	
		T entity = findOne(id);
		
		if (entity == null) {
			throw new ServiceException(SystemMessages.ENTITY_NOT_FOUND_ERRO);
		}

		markEntityForDeletion(entity);
		
		try {
			jpaRepository.saveAndFlush(entity);
		} catch (Exception ex) {
			throw new ServiceException(SystemMessages.ENTITY_REMOVE_ERRO, ex);
		}
	}
	
	/**
	 * method responsible activating an deactivating an Entity
	 * @param id
	 * @throws ServiceException
	 */
	@Transactional(rollbackFor = ServiceException.class)
	@Auditable(operationType=AuditingOperationType.ACTIVE_INACTIVE)
	public void activate(I id) throws ServiceException {
		
		T entity = findOne(id);
		
		if (entity == null) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND);
		}

		activateEntity(entity);

		try {
			jpaRepository.saveAndFlush(entity);
		} catch (Exception ex) {
			throw new ServiceException(SystemMessages.ENTITY_UPDATE_ERRO, ex);
		}

	}

	@Transactional(rollbackFor = ServiceException.class)
	@Auditable(operationType=AuditingOperationType.EDIT)
	public void edit(T entity) throws ServiceException {

		if (entity == null ) {
			throw new IllegalArgumentException(SystemMessages.ENTITY_PARAMETER_IS_NULL);
		}
		
		if (!entityExists(entity)) {
			throw new ServiceException(SystemMessages.ENTITY_NOT_FOUND_ERRO);
		} else {
			validateUniqueEntityData(entity);
			validateEntity(entity);
		}
		
		try {
			jpaRepository.saveAndFlush(entity);
		} catch (Exception ex) {
			throw new ServiceException(SystemMessages.ENTITY_UPDATE_ERRO , ex);
		}

	}
	
	public User getLoggedUser() {
		if (AuthUtil.getAuthenticatedUser() != null && AuthUtil.getAuthenticatedUser().getPrincipal() != null) {
			return (User) AuthUtil.getAuthenticatedUser().getPrincipal();
		}
		return null;
	}
}
