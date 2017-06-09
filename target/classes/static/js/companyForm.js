$(function(){		
	duplicateAddress();
	validatePhoneNumber();
	$(".cnpj").mask('99.999.999/9999-99');
	$(".cep").mask('99.999-999');
});

function validatePhoneNumber() {
	$("#phoneNumber").focusout(function(){
		var phone, element;
		element = $(this);
		element.unmask();
		phone = element.val().replace(/\D/g, '');
		if(phone.length > 10) {
			element.mask("(99) 99999-999?9");
		} else {
			element.mask("(99) 9999-9999?9");
		}
	}).trigger('focusout');	
}

function duplicateAddress() {
		
	$("#sameAddress").change(function(){
		if (this.checked) {
			$("#deliveryAddressFieldset").hide(function() {
				$("#deliveryAddressFieldset").attr("style", "display:none").attr("disabled", "disabled");
			});
		} else {
			$("#deliveryAddressFieldset").show(function() {
				$("#deliveryAddressFieldset").removeAttr("style").removeAttr("disabled");
			});			
		}
	}); 
}
