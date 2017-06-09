"use strict";

(function(main, undefined) {

	var _SUCCESS = 'SUCCESS', _WARNING = 'WARNING', _ERROR = 'DANGER', _INFO = 'INFO';
	
	main.initUserMessaging = function() {
		var userMessages = $("#user_messages");
		if (userMessages[0] === undefined) {
			var user_messages_container_html = '<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3 pull-right" id="user_messages"></div>';
			$('body > div.container').append(user_messages_container_html);
		}

	}

	main.isFunction = function(functionToCheck) {
		var getType = {};
		return functionToCheck
				&& getType.toString.call(functionToCheck) === '[object Function]';
	};

	main.alert_message = function(type, content, callback) {
		
		main.initUserMessaging();
		
		var div = document.createElement('div');
		$(div).load('alert', {
			'type' : type,
			'message' : content
		}, function(obj) {
			$('#user_messages').append(obj);
		});
		div = null;
		if (main.isFunction(callback))
			callback();
	};

	main.alert_success = function(content, callback) {
		main.alert_message(_SUCCESS, content, callback);
	};

	main.alert_error = function(content, callback) {
		main.alert_message(_ERROR, content, callback);
	};

	main.alert_warning = function(content, callback) {
		main.alert_message(_WARNING, content, callback);
	};

	main.alert_info = function(content, callback) {
		main.alert_message(_INFO, content, callback);
	};

	$(document).ready(function(){
		main.initUserMessaging();
	});
	
})(window.main = window.main || {});