package com.sicpa.thymeleaf.poc.aqualis.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.sicpa.thymeleaf.poc.aqualis.exception.ServiceException;
import com.sicpa.thymeleaf.poc.aqualis.persistence.entity.Page;
import com.sicpa.thymeleaf.poc.aqualis.persistence.repository.PageRepository;
import com.sicpa.thymeleaf.poc.aqualis.service.impl.PageServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class PageServiceTest {

	@InjectMocks
	private PageServiceImpl pageService;

	@Mock
	private PageRepository pageRepository;


	private Page createValidPage(Long id) {
		Page page = new Page();
		page.setId(id);
		page.setPath("user/edit");
		page.setAction("editUser");
		page.setTitle("Edit User");
		return page;
	}


	@Test
	public void findPageByPathValidPath() throws ServiceException {
		Page page = createValidPage(1l);
		when(pageRepository.findByPath(page.getPath())).thenReturn(page);
		pageService.findPageByPath("user/edit");
	}

	@Test(expected = ServiceException.class)
	public void findPageByPathValidPathException() throws ServiceException {
		Page page = createValidPage(100l);
		Mockito.doThrow(ServiceException.class).when(pageRepository).findByPath(page.getPath());
		pageService.findPageByPath("user/edit");
	}
	
	@Test
	public void findAll() throws ServiceException {
		
		List<Page> pages = Collections.emptyList();
		when(pageRepository.findAll()).thenReturn( pages );
		Assert.assertTrue( pageService.findAll().isEmpty());
	}

	@Test(expected = ServiceException.class)
	@SuppressWarnings("unchecked")
	public void testFindAllActionsByTitleException() throws ServiceException {
		Mockito.when(pageRepository.findAllActionsByTitle(Mockito.anyString())).thenThrow(Exception.class);
		
		pageService.findAllActionsByTitle("test");
	}	

	@Test
	public void testFindAllActionsByTitle() throws ServiceException {
		List<String> action_list = new ArrayList<>();
		action_list.add("action1");
		action_list.add("action2");
		action_list.add("action3");
		
		Mockito.when(pageRepository.findAllActionsByTitle(Mockito.anyString())).thenReturn(action_list);
		
		List<String> actions = pageService.findAllActionsByTitle("test");

		Assert.assertNotNull(actions);
		Assert.assertEquals(3, actions.size());	
	}	
	
	@Test(expected = ServiceException.class)
	@SuppressWarnings("unchecked")
	public void testFindAllTitlesException() throws ServiceException {
		Mockito.when(pageRepository.findAllTitles()).thenThrow(Exception.class);
		
		pageService.findAllTitles();
	}	

	@Test
	public void testFindAllTitles() throws ServiceException {
		List<String> title_list = new ArrayList<>();
		title_list.add("title1");
		title_list.add("title2");
		title_list.add("title3");
		
		Mockito.when(pageRepository.findAllTitles()).thenReturn(title_list);
		
		List<String> titles = pageService.findAllTitles();

		Assert.assertNotNull(titles);
		Assert.assertEquals(3, titles.size());	
	}	
}
