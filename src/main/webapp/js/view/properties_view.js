
serp.view.PropertiesView = Backbone.View.extend({

	template: serp.template("properties-template"),

	initialize: function (options) {
		console.log(options)
		this.propertyList = options.properties;
		this.render();
	},

	getPropertyInputEl: function (propertyName) {
		return this.$el.find("input[name='" + propertyName+"']");
	},

	collectData: function () {
		var properties = {};
		for (var i=0, len = this.propertyList.length; i <len; i++) {
			properties[this.propertyList[i].name] = this.getPropertyInputEl(this.propertyList[i].name).val();
		}
		return properties;
	},

	render: function () {
		this.$el.html(this.template({
			properties: this.propertyList
		}));
	}
});