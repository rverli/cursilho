package com.sicpa.thymeleaf.poc.aqualis.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.sicpa.thymeleaf.poc.aqualis.metadata.MetaData;

public class PageWrapper<T> {
	
    private static final String PAGE_PARAM = "page=";
	private static final String SORT_PARAM = "sort=";
	
	public static final int MAX_PAGE_ITEM_DISPLAY = 5;
    private Page<T> page;
    private List<PageItem> items;
    private int currentNumber;
    private String url;
    private MetaData metadata;
	private Sort sort;

    public PageWrapper(Page<T> page, String url, MetaData metadata, Sort sort){
        this.page = page;
        this.url = url;
        this.metadata = metadata;
        this.sort = sort;
        items = new ArrayList<>();

        currentNumber = page.getNumber() + 1; //start from 1 to match page.page

        int start;
        int size;
        if (page.getTotalPages() <= MAX_PAGE_ITEM_DISPLAY){
            start = 1;
            size = page.getTotalPages();
        } else {
            if (currentNumber <= MAX_PAGE_ITEM_DISPLAY - MAX_PAGE_ITEM_DISPLAY/2){
                start = 1;
                size = MAX_PAGE_ITEM_DISPLAY;
            } else if (currentNumber >= page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY/2){
                start = page.getTotalPages() - MAX_PAGE_ITEM_DISPLAY + 1;
                size = MAX_PAGE_ITEM_DISPLAY;
            } else {
                start = currentNumber - MAX_PAGE_ITEM_DISPLAY/2;
                size = MAX_PAGE_ITEM_DISPLAY;
            }
        }

        for (int i = 0; i<size; i++){
            items.add(new PageItem(start+i, (start+i)==currentNumber));
        }
    }

    public String getUrl() {
    	return url;
    }
    
    public void setUrl(String url) {
    	this.url = url;
    }
    
    public List<PageItem> getItems(){
        return items;
    }

    public int getNumber(){
        return currentNumber;
    }

    public List<T> getContent(){
        return page.getContent();
    }

    public int getSize(){
        return page.getSize();
    }

    public int getTotalPages(){
        return page.getTotalPages();
    }

    public boolean isFirstPage(){
        return page.isFirst();
    }

    public boolean isLastPage(){
        return page.isLast();
    }

    public boolean isHasPreviousPage(){
        return page.hasPrevious();
    }

    public boolean isHasNextPage(){
        return page.hasNext();
    }
    
    public Sort getSort(){
    	return this.sort;
    }
    
    public String getSortString(){
    	if( this.sort == null || !this.sort.iterator().hasNext() ){
    		return "";
    	}
    	
    	StringBuilder sortString = new StringBuilder();
    	for (Order order : sort) {
    		sortString.append(order.getProperty());
    		sortString.append(",");
    		sortString.append(order.getDirection().toString().toLowerCase());
    		sortString.append("&");
		}
    	return sortString.substring(0, sortString.length() - 1);
    }
    
    public String getUrlFromPageNumber(int number){
    	
    	if (url != null && url.length() > 0) {
        	url = setSortParam(url);
    	    url = setPageParam(url, number);
    	  }
    	
    	return url;
    }

	private String setPageParam(String url, int number) {
		
		if (url.contains(PAGE_PARAM)) {
			
			url = url.replaceAll(PAGE_PARAM+"[0-9]", PAGE_PARAM+number);
			
		} else { 
			
			// Otherwise, add it to end of query string
			if (url.contains("&")) {
				url = url.concat("&"+PAGE_PARAM+number);
				
			} else {
				url = url.concat("?"+PAGE_PARAM+number);
			}
			
		}
		
		return url;
	}

	private String setSortParam(String url) {
		
		if (url.contains(SORT_PARAM)){
			
			//get the position of sort param in the url
			int indexOfSortParam = url.indexOf(SORT_PARAM);
			
			//get all sort parameters starting from 'sort=' until the end of url
			String sortToReplace = url.substring(indexOfSortParam, url.length());
			
			//replace all sort parameters with new ones
			url = url.replaceAll(sortToReplace, SORT_PARAM+getSortString());
			
		}
		
		return url;
	}
    
    public class PageItem {
        private int number;
        private boolean current;

        public PageItem(int number, boolean current){
            this.number = number;
            this.current = current;
        }

        public int getNumber(){
            return this.number;
        }

        public boolean isCurrent(){
            return this.current;
        }
    }
    
    public MetaData getMetadata() {
		return metadata;
	}

}