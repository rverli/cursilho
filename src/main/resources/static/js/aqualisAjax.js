function updateCombo(from, to, url_to_call) {

    var value = $(from).val();
    var firstItem = $('#'+to+' option:eq(0)');
    var options = '';
    
    if (firstItem.val() == ''){
    	options = "<option value="+firstItem.val()+">"+firstItem.text()+"</option>";
    }

    if (value !== '') {

    	url_to_call = url_to_call + '/' + value;

    	$.ajax({
            url: url_to_call,
            success: function(result){

            	$.each(result, function(i, item) {
        			options = options+"<option value="+item.value+">"+item.label+"</option>";
            	});

            	$('#'+to).html(options);
            }
        });
    	
    } else {
    	
    	$('#'+to).html(options);
    	
    }

}
