$(function(){
	$("#modalButton").click(function(){
	    $.ajax({
	    	url: "/newUserAjax",
	    	type: "GET",
	    	success: function(result){	    		
	        	$("#modalContainer").html(result);
	    }});
	});

	$("body").on('click', '#submitAjax', function(e){
	    $.ajax({
	    	url: "../saveUserAjax",
	    	type: "POST",
	    	data:{
	    		'_csrf':$("input[name=_csrf]").val(), 
	    		'login':$("input[name=login]").val(),
	    		'name':$("input[name=name]").val(),
	    		'email':$("input[name=email]").val(),
	    		'password':$("input[name=password]").val(),
	    		'phoneNumber':$("input[name=phoneNumber]").val(),
	    		'active':$("input[name=active]").val(),
    		},	
    		success: function(result){

	    		if ($(result).find('.has-error').length) {
	    			$("#modalContainer").html(result);
    	        } else {

    	        	$("#myModal").modal('hide');
  	        		$(".panel-default").empty();
  	        		$(".panel-default").load("/adminAjax");
  	        		
    	        	bootstrap_alert.success("Usuário cadastrado com sucesso!");
              	}

    		},
	    });
	});
	
	//generate alert dynamically
    bootstrap_alert = function() {}
    bootstrap_alert.success = function(message) {
        $('#ajaxMessages').html('<div class="alert alert-dismissable alert-success"><button type="button" class="close" data-dismiss="alert">&times;</button><span>'+message+'</span></div>')
    }	
	
});	