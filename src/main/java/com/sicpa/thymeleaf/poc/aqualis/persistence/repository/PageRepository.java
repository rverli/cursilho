package com.sicpa.thymeleaf.poc.aqualis.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interface for Page Entity
 * @author mjimenez
 *
 */
public interface PageRepository extends JpaRepository<com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Page, Long> {
	/**
	 * <p>
	 * Locate a single page using the exact path informed
	 * </p>
	 * @param path
	 * @return
	 */
	com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Page findByPath(@Param("path") String path);

	/**
	 * <p>
	 * List all distinct actions from selected title
	 * </p>
	 * @param title
	 * @return
	 */
	@Query("select distinct action from Page p where title = :title order by action")
	List<String> findAllActionsByTitle(@Param("title") String title);

	/**
	 * <p>
	 * List all distinct titles
	 * </p>
	 * @return
	 */
	@Query("select distinct title from Page order by title")
	List<String> findAllTitles();
}
