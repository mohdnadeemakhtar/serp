
serp.view.CreateRelationshipView = Backbone.View.extend({

	template: serp.template("create-relationship-template"),

	initialize: function () {
		var that = this;
		$.get("/api/getRelationshipTypeList").done(function(response) {
			that.relationshipTypeList = response;
			that.render();
		});
	},

	render: function () {
		this.$el.html(this.template({
			relationTypes: this.relationshipTypeList,
		}));

		this.nodeSelector1 = new serp.view.NodeSelectorView({
			el: ".create_relationship_node[data-nr='1']",
		});
		this.nodeSelector2 = new serp.view.NodeSelectorView({
			el: ".create_relationship_node[data-nr='2']",
		});
	},

});