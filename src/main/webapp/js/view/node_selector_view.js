serp.view.NodeSelectorView = Backbone.View.extend ({
	
	template: serp.template("node-selector-template"),
	nodesTemplate: serp.template("node-dropdown-template"),
	
	initialize: function () {
		var that = this;
		$.get("/api/getNodeTypeList").done(function(response){
			that.nodeTypeList = response;
			that.render();
			that.showNodes(that.nodeTypeList[0].name);
		});
	},
	
	
	showNodes: function (nodeType) {
		var that = this;
		$.get("/api/getNodesOfType", {type: nodeType}).done(function(response) {
			that.$el.find(".node_selector_node").html(that.nodesTemplate({
				nodes: response
			}));
		});
	},

	render: function () {
		this.$el.html(this.template({nodeTypes: this.nodeTypeList}));
	},
	
});