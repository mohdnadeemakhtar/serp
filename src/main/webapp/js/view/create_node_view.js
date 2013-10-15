
serp.view.CreateNodeView = Backbone.View.extend({
	
	template: serp.template("create-node-template"),
	
	initialize: function () {

		var modelData = {
			nodeTypes: [
			            {type: "person", label: "Person"}],	
		};
		
		
		this.$el.html(this.template(modelData));
	},
	
	render: function () {
		this.$el.html(this.template(this.collection));
	}
	
});