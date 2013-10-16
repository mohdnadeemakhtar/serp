
// Namespaces
var serp = {};
serp.view = {};

//change underscore.js template syntax to: {{=}} (interpolate variables) and {{}} (execute arbitrary JavaScript code)
_.templateSettings = {
	interpolate : /\{\{ue\=(.+?)\}\}/g, // /\{\{\=(.+?)\}\}/g,
	escape : /\{\{e?\=(.+?)\}\}/g, // /\{\{e\=(.+?)\}\}/g,
	evaluate : /\{\{(.+?)\}\}/g
};

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
 * @param templateName
 *            Name of template
 * @result compiled underscore.js template function
 */
serp.TEMPLATE_CACHE = {};
serp.template = function(templateName, doRenderTemplateName) {
	
	if (doRenderTemplateName === undefined) {
		doRenderTemplateName = true;
	}
	
	if (!serp.TEMPLATE_CACHE[templateName]) {
		var templObj = jQuery('#' + templateName);
		if (templObj.length === 0) {
			throw "Hello, the template '" + templateName + "' was not found!";
		}
		
		if (doRenderTemplateName) {
			// write template name to html code for faster debugging
			serp.TEMPLATE_CACHE[templateName] = _.template('\n<!-- '
					+ templateName + ' -->\n' + templObj.html());
		}
		else {
			serp.TEMPLATE_CACHE[templateName] = _.template(templObj.html());
		}
	}

	return serp.TEMPLATE_CACHE[templateName];
};

serp.login = function (username, password) {
	return $.post ("/login", {
		username: username,
		password: password
	});
}
