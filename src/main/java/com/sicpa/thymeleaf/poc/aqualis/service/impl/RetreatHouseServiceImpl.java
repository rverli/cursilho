package com.sicpa.thymeleaf.poc.aqualis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Address;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.RetreatHouse;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.RetreatHouseRepository;
import com.sicpa.thymeleaf.poc.aqualis.service.AbstractCrudService;
import com.sicpa.thymeleaf.poc.aqualis.service.RetreatHouseService;

@Service
public class RetreatHouseServiceImpl extends AbstractCrudService<RetreatHouse, Long> implements RetreatHouseService {
	
	private RetreatHouseRepository retreatHouseRepository;
	
	@Autowired
	public RetreatHouseServiceImpl(RetreatHouseRepository retreatHouseRepository) {
		super(retreatHouseRepository);
		this.retreatHouseRepository = retreatHouseRepository;
	}
		
	@Override
	protected void validateEntity(RetreatHouse retreatHouse) throws ServiceException {
				
		if( retreatHouse.getAddress() != null ){
			for (Address address : retreatHouse.getAddress()) {
				address.setRetreatHouse(retreatHouse); //set the company reference in addresses
			}
		}

	}
	
	@Override
	protected boolean entityExists(RetreatHouse retreatHouse) {
		if( retreatHouse.getId() == null ){
			throw new IllegalArgumentException(SystemMessages.ENTITY_PARAMETER_IS_NULL);
		}
		return retreatHouseRepository.findOne(retreatHouse.getId()) != null;
	}

	@Override
	protected void activateEntity(RetreatHouse retreatHouse) {
		retreatHouse.setActive(!retreatHouse.isActive());
	}
	
	@Override
	protected void markEntityForDeletion(RetreatHouse retreatHouse) {
		retreatHouse.setDeleted(true);
	}
	
	/**
	 * <p>
	 * Validate information that is unique for every company
	 * </p>
	 * @param retreatHouse
	 * @throws ServiceException
	 */
	@Override
	protected void validateUniqueEntityData(RetreatHouse retreatHouse) throws ServiceException {
		/*if (cpfExists(retreatHouse)) {
			throw new ServiceException(SystemMessages.CPF_UNIQUE);
		}*/
	}
	
	/**
	 * <p>
	 * 	Locate companies using parameters informed.
	 * </p>
	 * @param name
	 * @param alias
	 * @param cnpj
	 * @param active
	 * @param pageable
	 */
	@Override
	public Page<RetreatHouse> findByNameAndResponsableAndActiveContaining(String nome, String responsable, Boolean active, Pageable pageable) throws ServiceException {
		
		Page<RetreatHouse> retreatHouseList;
		try {
			retreatHouseList = retreatHouseRepository.findByNameAndResponsableAndActiveContaining( nome, responsable, active, pageable );
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}		
		return retreatHouseList;
	}

	/**
	 * <p>
	 * Delegates call to Repository and returns a {@link List} of {@link Company} populated with id and fantasyName
	 * </p>
	 */
	@Override
	public List<RetreatHouse> findAllRetreatHouses() throws ServiceException {
		
		List<RetreatHouse> retreatHouseList;
		try {
			retreatHouseList = retreatHouseRepository.findRetreatHouseWithIdAndName();
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}		
		return retreatHouseList;
	}

	@Override
	public List<RetreatHouse> findActiveRetreatHouses(boolean active) throws ServiceException {
		
		List<RetreatHouse> retreatHouseList;
		try {
			retreatHouseList = retreatHouseRepository.findByActiveOrderByName(active);
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}		
		return retreatHouseList;
	}

}
