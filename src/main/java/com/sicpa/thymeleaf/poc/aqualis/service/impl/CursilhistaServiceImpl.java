package com.sicpa.thymeleaf.poc.aqualis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Address;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Cursilhista;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.CursilhistaRepository;
import com.sicpa.thymeleaf.poc.aqualis.service.AbstractCrudService;
import com.sicpa.thymeleaf.poc.aqualis.service.CursilhistaService;

@Service
public class CursilhistaServiceImpl extends AbstractCrudService<Cursilhista, Long> implements CursilhistaService {
	
	private CursilhistaRepository cursilhistaRepository;
	
	@Autowired
	public CursilhistaServiceImpl(CursilhistaRepository cursilhistaRepository) {
		super(cursilhistaRepository);
		this.cursilhistaRepository = cursilhistaRepository;
	}
	
	@Override
	protected void validateEntity(Cursilhista cursilhista) throws ServiceException {
				
		if( cursilhista.getAddress() != null ){
			for (Address address : cursilhista.getAddress()) {
				address.setCursilhista(cursilhista); //set the company reference in addresses
			}
		}
	}
	
	@Override
	protected boolean entityExists(Cursilhista cursilhista) {
		if( cursilhista.getId() == null ){
			throw new IllegalArgumentException(SystemMessages.ENTITY_PARAMETER_IS_NULL);
		}
		return cursilhistaRepository.findOne(cursilhista.getId()) != null;
	}

	@Override
	protected void activateEntity(Cursilhista cursilhista) {
		cursilhista.setActive(!cursilhista.isActive());
	}
	
	@Override
	protected void markEntityForDeletion(Cursilhista cursilhista) {
		cursilhista.setDeleted(true);
	}
	
	/**
	 * <p>
	 * Validate information that is unique for every company
	 * </p>
	 * @param company
	 * @throws ServiceException
	 */
	@Override
	protected void validateUniqueEntityData(Cursilhista cursilhista) throws ServiceException {
		
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
	public Page<Cursilhista> findByNumberCursilhistaAndNameAndRetreatNumberAndActiveContaining(String numero, String nome, Long retreatNumber, Boolean active, Pageable pageable) throws ServiceException {
		
		Page<Cursilhista> cursilhistaList = null;
		
		try {
			if ( numero != null && numero.equals("0") ) {
				cursilhistaList = cursilhistaRepository.findCursilhistaWithoutNumberCursilhista(nome, retreatNumber, active, pageable);
			} else {
				cursilhistaList = cursilhistaRepository.findByNumberCursilhistaAndNameAndRetreatNumberAndActiveContaining(numero, nome, retreatNumber, active, pageable);
			}
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}		
		return cursilhistaList;
	}
	
	/**
	 * <p>
	 * Delegates call to Repository and returns a {@link List} of {@link Company} populated with id and fantasyName
	 * </p>
	 */
	@Override
	public List<Cursilhista> findAllCursilhistas() throws ServiceException {
		List<Cursilhista> companyList;
		try {
			companyList = cursilhistaRepository.findCursilhistaWithIdAndName();
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}		
		return companyList;
	}

	@Override
	public List<Cursilhista> findActiveCursilhistas(boolean active) throws ServiceException {
		List<Cursilhista> companyList;
		try {
			companyList = cursilhistaRepository.findByActiveOrderByName(active);
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}		
		return companyList;
	}

	@Override
	public Long findLastNumberCursilhista() throws ServiceException {
		
		List<Cursilhista> c = null;
		
		try {
			c = cursilhistaRepository.findLastNumberCursilhista();
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}
		
		return c != null ? c.get(0).getNumberCursilhista() : null;
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
	public Page<Cursilhista> findByNameAndRetreatNumberAndActiveContaining(String name, Long retreatNumber, Boolean active, Pageable pageable) throws ServiceException {
		Page<Cursilhista> cursilhistaList;
		try {
			cursilhistaList = cursilhistaRepository.findByNameAndRetreatNumberAndActiveContaining(name, retreatNumber, active, pageable);
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}		
		return cursilhistaList;
	}
	
}
