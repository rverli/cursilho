package com.sicpa.thymeleaf.poc.aqualis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Retreat;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.RetreatRepository;
import com.sicpa.thymeleaf.poc.aqualis.service.AbstractCrudService;
import com.sicpa.thymeleaf.poc.aqualis.service.RetreatService;

@Service
public class RetreatServiceImpl extends AbstractCrudService<Retreat, Long> implements RetreatService {
	
	private RetreatRepository retreatRepository;
	
	@Autowired
	public RetreatServiceImpl(RetreatRepository retreatRepository) {
		super(retreatRepository);
		this.retreatRepository = retreatRepository;
	}
		
	@Override
	protected void validateEntity(Retreat retreat) throws ServiceException {
		
	}
	
	@Override
	protected boolean entityExists(Retreat retreat) {
		if( retreat.getId() == null ){
			throw new IllegalArgumentException(SystemMessages.ENTITY_PARAMETER_IS_NULL);
		}
		return retreatRepository.findOne(retreat.getId()) != null;
	}

	@Override
	protected void activateEntity(Retreat retreatHouse) {
		retreatHouse.setActive(!retreatHouse.isActive());
	}
	
	@Override
	protected void markEntityForDeletion(Retreat retreatHouse) {
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
	protected void validateUniqueEntityData(Retreat retreatHouse) throws ServiceException {
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
	public Page<Retreat> findByNumberAndCoordinatorAndRetreatHouseAndActiveContaining(Long number, String coordinator, String retreatHouse, Boolean active, Pageable pageable) throws ServiceException {
		
		Page<Retreat> retreatList;
		try {
			retreatList = retreatRepository.findByNumberAndCoordinatorAndRetreatHouseAndActiveContaining( number, coordinator, retreatHouse, active, pageable );
						
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}		
		return retreatList;
	}

	/**
	 * <p>
	 * Delegates call to Repository and returns a {@link List} of {@link Company} populated with id and fantasyName
	 * </p>
	 */
	@Override
	public List<Retreat> findAllRetreats() throws ServiceException {
		
		List<Retreat> retreatList;
		try {
			retreatList = retreatRepository.findRetreatWithIdAndNumber();
			
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}		
		return retreatList;
	}

	@Override
	public List<Retreat> findActiveRetreats(boolean active) throws ServiceException {
		
		List<Retreat> retreatList;
		try {
			retreatList = retreatRepository.findByActiveOrderByNumber(active);
			
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}		
		return retreatList;
	}

	@Override
	public Long findLastNumberRetreat() throws ServiceException {
		
		List<Retreat> c = null;
		
		try {
			c = retreatRepository.findLastNumberRetreat();
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}
		
		return c != null ? c.get(0).getNumber() : null;
	}

	@Override
	public Retreat findRetreatById(Long id) throws ServiceException {
		Retreat c = null;
		
		try {
			c = retreatRepository.findRetreatById(id);
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}
		
		return c;
	}

	@Override
	public Page<Retreat> findRetreatByIdPage(Long id, Pageable pageable) throws ServiceException {
		Page<Retreat> c = null;
		
		try {
			c = retreatRepository.findRetreatByIdPage(id, pageable);
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}
		
		return c;
	}
}
