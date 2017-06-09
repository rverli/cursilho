package com.sicpa.thymeleaf.poc.aqualis.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Pageable utility tools.
 *
 */
public class PageableUtil {
	
	private PageableUtil() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Returns a new {@link PageRequest} for {@link Pageable} and page size;
	 * @param source the pageable list.
	 * @param pageSize the page size.
	 * @return a new {@link PageRequest}
	 */
	public static PageRequest getPageRequest(final Pageable source, final int pageSize)
	{
	    return new PageRequest(source.getPageNumber(), pageSize, source.getSort());
	}
	
	/**
	 * Returns a new {@link PageRequest} for {@link Pageable} and page size;
	 * @param source the pageable list.
	 * @param pageSize the page size.
	 * @param sort the page.
	 * @return a new {@link PageRequest}
	 */
	public static PageRequest getPageRequest(final Pageable source, final int pageSize, Sort sort)
	{
	    return new PageRequest(source.getPageNumber(), pageSize, sort);
	}
}
