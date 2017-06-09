$(function(){		
	//duplicateAddress();
	validatePhoneNumber();
	validateMobileNumber();
	validateWorkNumber();
	validatePhoneNumberPresenter();
	validateMobileNumberPresenter();
	validateWorkNumberPresenter();
	$(".cpf").mask('999.999.999-99');
	$(".cep").mask('99.999-999');
	//$(".identity").mask('99.999.999-9');
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

function validateMobileNumber() {
	$("#mobileNumber").focusout(function(){
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

function validateWorkNumber() {
	$("#workNumber").focusout(function(){
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

function validatePhoneNumberPresenter() {
	$("#phoneNumberPresenter").focusout(function(){
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

function validateMobileNumberPresenter() {
	$("#mobileNumberPresenter").focusout(function(){
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

function validateWorkNumberPresenter() {
	$("#workNumberPresenter").focusout(function(){
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

function validate(evt) {
  var theEvent = evt || window.event;
  var key = theEvent.keyCode || theEvent.which;
  key = String.fromCharCode( key );
  var regex = /[0-9]|\./;
  if( !regex.test(key) ) {
    theEvent.returnValue = false;
    if(theEvent.preventDefault) theEvent.preventDefault();
  }
}
