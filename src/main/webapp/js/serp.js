
// Namespaces
var serp = {};
serp.view = {};
serp.model = {};

//change underscore.js template syntax to: {{=}} (interpolate variables) and {{}} (execute arbitrary JavaScript code)
_.templateSettings = {
	interpolate : /\{\{ue\=(.+?)\}\}/g, // /\{\{\=(.+?)\}\}/g,
	escape : /\{\{e?\=(.+?)\}\}/g, // /\{\{e\=(.+?)\}\}/g,
	evaluate : /\{\{(.+?)\}\}/g
};

// add beforeClose
Backbone.View.prototype.close = function() {
	if (this.beforeClose) {
		this.beforeClose();
	}
	this.undelegateEvents();
	this.remove();
	this.unbind();
};

/**
 * get (cached) template
 * @param name
 *            Name of template
 * @result compiled underscore.js template function
 */
serp.TEMPLATE_CACHE = {};

serp.template = function (name) {
	return function (data) {
		if (!serp.TEMPLATE_CACHE[name])  {
			throw "Template not found: " + name;
		}
		return serp.TEMPLATE_CACHE[name](data);
	};
}

// load (currently all) templates via ajax
serp.loadTemplates = function (success) {
	$.get("/ajax/templates").done(function (response) {
		for (templateName in response) {
			console.log("check "+templateName);
			serp.TEMPLATE_CACHE[templateName] = _.template(response[templateName]);
		}
		success();
	});
}

serp.login = function (username, password) {
	return $.post ("/login", {
		username: username,
		password: password
	});
}

serp.logout = function () {
	return $.post ("/logout");
}
