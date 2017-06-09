"use strict";

var ajaxProperties ={};

$(document).ready(function() {
	$('[data-toggle="tooltip"], a.delete').tooltip();
	$('[data-toggle="tooltip"], a.confirm').tooltip();

	$('#myModal').modal({
		keyboard : false,
		backdrop : 'static',
		show : false
	});

	// cancel button behaviour
	$('#myModal').find('button.btn-default').click(function() {
		var toggle_Check = $(this).data('toggle-check');
		if(toggle_Check) {
			var id = $(this).val();
			toggleCheck(id);
		}
	});

	$('input.activate').click(function(event){
		var element = $(event.currentTarget);
		var id = element.data('id');
		var title = element.data('title');
		var content = element.data('content');

		updateModal(id, title, content);

		$('#myModal').find('button.btn-default').data('toggle-check', true);

		ajaxProperties = {
			url: 'active',
			type: 'PUT',
			dataType: 'json',
			data:{'id':id},
			success : function(responseObj) {
				main.alert_success(responseObj.message);
			},
			error : function(responseObj) {
				main.alert_error(responseObj.responseJSON.message, function() {
					hideModal();
					toggleCheck(id);
				});
			},
			complete : hideModal
		};
	});

	$('a.delete').click(function(event) {
		var element = $(event.currentTarget);
		var id = element.data('id');
		var title = element.data('title');
		var content = element.data('content');

		updateModal(id, title, content);

		
		$('#myModal').find('button.btn-default').data('toggle-check', false);

		ajaxProperties = {
			url : 'delete/' + id,
			type : 'DELETE',
			dataType : 'json',
			success : function(responseObj) {
				main.alert_success(responseObj.message, function() {
					removeLinhaUser(id);
				});
			},
			error : function(responseObj) {
				main.alert_error(responseObj.message);
			},
			complete : hideModal
		};
	});

	$('a.confirm').click(function(event) {
		var element = $(event.currentTarget);
		var id = element.data('id');
		var title = element.data('title');
		var content = element.data('content');
		var url = element.data('url');
		var table_update = element.data('table-update');

		updateModal(id, title, content);

		$('#myModal').find('button.btn-default').data('toggle-check', false);

		ajaxProperties = {
			url : url + '/' + id,
			type : 'PUT',
			dataType : 'json',
			data: {'id':id},
			success : function(responseObj) {
				main.alert_success(responseObj.message, function() {
					if (table_update == 'update') {
						// TODO Update just row
					} else if (table_update == 'delete') {
						removeLinhaUser(id);
					} else if (table_update == 'reload') {
						window.location.reload(false); 
					}
				});
			},
			error : function(responseObj) {
				main.alert_error(responseObj.responseJSON.message);
			},
			complete : hideModal
		};
	});
	
	// Confirm button behaviour
	var confirmButton = $('#myModal').find('button.btn-primary');
	confirmButton.click(function(){
		$.ajax(ajaxProperties);
	});
});

function updateModal(id, title, content) {
	var modal = $('#myModal');
	modal.find('.modal-title').text(title);
	modal.find('.modal-body').text(content);
	modal.find('button.btn-primary').val(id);
	modal.find('button.btn-default').val(id);
}

function hideModal() {
	$('#myModal').modal('hide');
}

function toggleCheck(dataid) {
	var elementSelector = 'input[data-id="' + dataid + '"]';
	$(elementSelector).prop("checked",
			!$(elementSelector).prop("checked"));
}

function removeLinhaUser(dataid){
	var elementSelector = 'a[data-id="'+dataid+'"]';
	var tableRow = $(elementSelector).closest("TR");
	tableRow.remove();
}

function retrieveUsers() {
    var url = '/admin';

    if ($('#login').val() !== '') {
        url = url + '?login=' + $('#login').val();
    }

    $("#table").load(url);
}