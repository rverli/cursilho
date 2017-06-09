package com.sicpa.thymeleaf.poc.aqualis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Profile;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.ProfileRepository;
import com.sicpa.thymeleaf.poc.aqualis.service.AbstractCrudService;
import com.sicpa.thymeleaf.poc.aqualis.service.ProfileService;

/**
 * Profile Service Implementation
 * @author lrosa1
 *
 */
@Service
public class ProfileServiceImpl extends AbstractCrudService<Profile, Long> implements ProfileService {
	
	private ProfileRepository profileRepository;
	
	/**
	 * Create a new profile service instance
	 * @param profileRepository
	 */
	@Autowired
	public ProfileServiceImpl(ProfileRepository profileRepository) {
		super(profileRepository);
		this.profileRepository = profileRepository;
	}

	@Override
	protected void markEntityForDeletion(Profile entity) {
		// not necessary implementation yet
	}

	@Override
	protected void validateEntity(Profile entity) throws ServiceException {
		//not necessary implementation yet
	}

	@Override
	protected void activateEntity(Profile entity) {
		//not necessary implementation yet
	}

	@Override
	protected void validateUniqueEntityData(Profile entity) throws ServiceException {
		//not necessary implementation yet
	}

	@Override
	protected boolean entityExists(Profile entity) throws ServiceException {
		return false;
	}

	@Override
	public List<Profile> findAll() throws ServiceException {
		List<Profile> profiles;
		try {
			profiles = profileRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException(SystemMessages.ENTITY_APP_ERROR_FIND, e);
		}
		return profiles;
	}

}
