package com.sicpa.thymeleaf.poc.aqualis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Cursilhista;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.CursilhistaRepository;
import com.sicpa.thymeleaf.poc.aqualis.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private CursilhistaRepository cursilhistaRepository;

	@Override
	public List<Cursilhista> findByIdRetreat(Long idRetreat) throws ServiceException {
		
		List<Cursilhista> cursilhistas = cursilhistaRepository.findByReport( idRetreat );
		
		return cursilhistas;
	}	
}
