package com.sicpa.thymeleaf.poc.aqualis.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.messages.SystemMessages;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Page;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.PageRepository;
import com.sicpa.thymeleaf.poc.aqualis.service.PageService;

/**
 * Performs operations for a {@link Page}
 * @author mjimenez
 *
 */
@Service
public class PageServiceImpl implements PageService  {
	
	@Autowired(required=true)
	private PageRepository pageRepository;
	
	private static final Logger logger = Logger.getLogger(PageServiceImpl.class);
	
	@Override
	public Page findPageByPath(String path) throws ServiceException {
		Page page = null;
		try {
			page = pageRepository.findByPath(path);
		} catch (Exception e) {
			logger.error(e);
			throw new ServiceException(SystemMessages.APP_SERVICE_ERROR_FIND_PAGE_WITH_PARAMETERS_KEY, e);
		}
		return page;
	}

	@Override
	public List<Page> findAll() {
		return pageRepository.findAll();
	}
	
	@Override
	public List<String> findAllActionsByTitle(String title) throws ServiceException {
		
		List<String> actions = null;
		
		try {
			actions = pageRepository.findAllActionsByTitle(title);
		} catch (Exception e) {
			logger.error("Erro ao listar ações", e);
			throw new ServiceException(SystemMessages.SYSTEM_ERROR, e);
		}
		
		return actions;
	}
	
	@Override
	public List<String> findAllTitles() throws ServiceException {
		
		List<String> titles = null;
		
		try {
			titles = pageRepository.findAllTitles();
		} catch (Exception e) {
			logger.error("Erro ao listar interfaces", e);
			throw new ServiceException(SystemMessages.SYSTEM_ERROR, e);
		}
		
		return titles;
	}

}
