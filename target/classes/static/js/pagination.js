"use strict";

var SORT_DIRECTION = {
	"ASC":{direction:"asc", styleclass:"sorting_asc",newDirection:"desc", newstyleclass:"sorting_desc"},
	"DESC":{direction:"desc", styleclass:"sorting_desc",newDirection:"asc", newstyleclass:"sorting_asc"}
};

(function(main){

	main.paginationAction = "";
	
	/**
	 * <p>
	 * Initialisation of the ordering of a table, to enable ordering of a column a table has to have a <code>class</code> </b>dataTable<b>
	 * the data-order attribute of the table indicates the current order of the table.
	 * and the column has to have an attribute data-order with a value indicating the property to order by and the direction asc or desc.</br>
	 * example:</b>
	 * <code>
	 * <table class="dataTable" data-order="name,asc">
	 * <thead>
	 * 	<th class="sorting" data-order="email,desc">email</th>
	 * </thead>
	 * </code>
	 * 
	 * </p>
	 */
	main.initTableSorting = function(){
		var DATA_ORDER_ATTRIBUTE_NAME = "data-order";
        $("TABLE.dataTable").find("TH[" + DATA_ORDER_ATTRIBUTE_NAME + "]").each( function(){
        	var sort = $(this).parents("TABLE").attr(DATA_ORDER_ATTRIBUTE_NAME);
        	
        	if( sort ){
        		var sortProperty  = sort.split(",")[0];
        		
        		if( $(this).attr(DATA_ORDER_ATTRIBUTE_NAME).indexOf(sortProperty) >=0 ){
            		var sortDirection = SORT_DIRECTION[sort.split(",")[1].toUpperCase()];
    				$(this).attr(DATA_ORDER_ATTRIBUTE_NAME, sortProperty + "," + sortDirection.newDirection )
    				$(this).removeClass()
    				$(this).addClass(sortDirection.newstyleclass)
        		}else{
        			$(this).removeClass()
        			$(this).addClass("sorting")
        		}
        	}else{
    			$(this).removeClass()
    			$(this).addClass("sorting")
        	}
        	
        	$(this).click(function(){
        		main.updateSort('sort',$(this).attr(DATA_ORDER_ATTRIBUTE_NAME));
        	});
        });
	}
	
	main.initTableSorting();
	
	main.updateSort = function (key, value)
	{
	    var key = encodeURI(key); 
	    var value = encodeURI(value);
	    var kvp = window.location.search.substr(1).split('&');
	    var i=kvp.length; var x; 
	    
	    while(i--) {
	        x = kvp[i].split('=');
	        if (x[0]==key) {
	            x[1] = value;
	            kvp[i] = x.join('=');
	            break;
	        } 
	    }

	    if(i<0) {
	    	kvp[kvp.length] = [key,value].join('=');
	    }

	    //this will reload the page, it's likely better to store this until finished
	    window.location.search = kvp.join('&'); 
	}
	
})( window.main = window.main || {});
