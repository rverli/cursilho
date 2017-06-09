package com.sicpa.thymeleaf.poc.aqualis.service;

import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Audit;

public interface AsyncService {

	void doAsync(Audit audit);
}
