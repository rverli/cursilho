package com.sicpa.thymeleaf.poc.aqualis.service;

import java.util.List;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Cursilhista;

public interface ReportService {

	List<Cursilhista> findByIdRetreat(Long idRetreat) throws ServiceException;
}
